package easy.app.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;

/**
 * Created by Lucio on 17/3/23.
 */

public class Downloader {

    /**
     * 开始下载任务
     * @param params
     * @return 下载任务
     */
    public static DownloadTask startRequest(Context context, DownloadTask.DownloadRequestParams params, DownloadTask.OnDownloadListener listener) {
        if (!isDownloadManagerAvailable(context))
            throw new RuntimeException("download manager is not available.");

        String url = params.mUrl;
        if (url == null || url.length() == 0) {
            throw new RuntimeException("download url invalid.");
        }

        DownloadManager dm = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

        long id = DownloadRequestCache.getExistRequestId(context, url);
        //已经存在现在任务
        if (id != DownloadTask.INVALID_DOWNLOAD_ID && id > 0) {
            String fileName = DownloadManagerQuery.getFileName(dm, id);
            File file = new File(fileName);
            //如果队列中存在任务，但是本地文件已经被删除，则移除队列id，清除缓存id
            if (!file.exists() || !file.isFile()) {
                removeDownloadTask(context, params.mUrl, id);
                DownloadRequestCache.setRequestId(context, url, DownloadTask.INVALID_DOWNLOAD_ID);
                //重新入队请求
                id = dm.enqueue(params.build());
                DownloadRequestCache.setRequestId(context, url, id);
            }
        } else {
            //任务入队执行
            id = dm.enqueue(params.build());
            DownloadRequestCache.setRequestId(context, url, id);
        }
        return new DownloadTask(dm, id, params, listener);
    }


    /**
     * 移除下载任务
     *
     * @param context
     * @param id
     */
    public static void removeDownloadTask(Context context, String url, long id) {
        DownloadManager dm = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        dm.remove(id);
        DownloadRequestCache.setRequestId(context, url, id);
    }


    /**
     * 下载管理器是否被禁用
     *
     * @param context
     * @return
     */
    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
                return false;
            }

            int code = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (code == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                    || code == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 任务id缓存
     */
    private static final class DownloadRequestCache {

        private static final String FILE_NAME = "DOWNLOAD_ID_CACHE";

        /**
         * 获取任务ID
         *
         * @param context
         * @param url     下载地址
         * @return
         */
        public static long getExistRequestId(Context context, String url) {
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            return sp.getLong(url, DownloadTask.INVALID_DOWNLOAD_ID);
        }

        /**
         * 保存任务ID
         *
         * @param context
         * @param url     下载地址
         * @param id      ID
         */
        public static void setRequestId(Context context, String url, long id) {
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(url, id);
            editor.apply();
        }
    }

}
