package easy.app.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;


/**
 * Created by Lucio on 17/3/23.
 * 下载任务
 */
public class DownloadTask {

    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");

    /**
     * 不可用的下载ID
     */
    static final long INVALID_DOWNLOAD_ID = -1;

    private long mId;
    private DownloadManager mDm;
    private Handler mHandler;
    DownloadRequestParams mRequest;
    OnDownloadListener mListener;
    boolean isRegisterContentObserver = false;
    /**
     * 数据更新观察
     */
    private ContentObserver mContentObserver;
    /**
     * 下载完成Receiver
     */
    private CompleteReceiver mCompleteReceiver;

    static final int WHAT = 0, UPDATE_STATUS = 1;

    /**
     * @param dm       下载管理器
     * @param id       任务id
     * @param request  任务参数
     * @param listener 回调
     */
    public DownloadTask(DownloadManager dm, long id, DownloadRequestParams request, OnDownloadListener listener) {
        mId = id;
        mDm = dm;
        mRequest = request;
        mListener = listener;
        mHandler = new InnerHandler();
        updateDataAndStatus();
    }

    /**
     * 获取任务Id
     *
     * @return 任务id
     */
    public long getTaskId() {
        return mId;
    }

    /**
     * 更新任务的进度和状态
     */
    private void updateDataAndStatus() {
        if (mId == INVALID_DOWNLOAD_ID) {
            mHandler.sendMessage(mHandler.obtainMessage(WHAT, 0, 0, TaskStatus.ERROR_TASK));
        } else {
            int[] bytesAndStatus = DownloadManagerQuery.getBytesAndStatus(mDm, mId);
            mHandler.sendMessage(mHandler.obtainMessage(WHAT, bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2]));
        }
    }

    /**
     * 注册下载成功接收广播
     * (只需注册一次，在不需要使用的时候反注册：{@link #unregisterCompleteReceiver(Activity)})
     *
     * @param activity
     */
    public void registerCompleteReceiver(Activity activity) {
        if (mCompleteReceiver == null) {
            mCompleteReceiver = new CompleteReceiver(mHandler, mId);
        }
        activity.registerReceiver(mCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 反注册下载成功接收广播
     *
     * @param activity
     */
    public void unregisterCompleteReceiver(Activity activity) {
        if (mCompleteReceiver != null)
            activity.unregisterReceiver(mCompleteReceiver);

    }

    /**
     * 注册下载进度观察
     * 可以在{@link Activity#onResume()}中注册
     *
     * @param activity
     */
    public void registerContentResolver(Activity activity) {
        if (mContentObserver == null) {
            mContentObserver = new ContentObserver(mHandler) {
                @Override
                public void onChange(boolean selfChange) {
                    updateDataAndStatus();
                }
            };
        }
        if (!isRegisterContentObserver) {
            activity.getContentResolver().registerContentObserver(CONTENT_URI, true, mContentObserver);
            isRegisterContentObserver = true;
        }
    }

    /**
     * 反注册内容观察
     * 可以在{@link Activity#onPause()}中反注册
     *
     * @param activity
     */
    public void unregisterContentResolver(Activity activity) {
        if (mContentObserver != null) {
            isRegisterContentObserver = false;
            activity.getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }

    /**
     * {@link Activity#onResume()}
     *
     * @param activity
     */
    public void onResume(Activity activity) {
        registerContentResolver(activity);
        updateDataAndStatus();
    }

    /**
     * {@link Activity#onPause()}
     *
     * @param activity
     */
    public void onPause(Activity activity) {
        unregisterContentResolver(activity);
    }

    /**
     * {@link Activity#onDestroy()}
     *
     * @param activity
     */
    public void onDestroy(Activity activity) {
        unregisterContentResolver(activity);
        unregisterCompleteReceiver(activity);
    }

    private class InnerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                switch (msg.what) {
                    case WHAT:
                        @TaskStatus int status = (Integer) msg.obj;// 下载状态
                        int max = msg.arg2;
                        int downloaded = msg.arg1;
                        if (mListener != null) {
                            mListener.onDownloadChanged(status, max, downloaded);
                            if(status == TaskStatus.SUCCESS){
                                mListener.onDownloadSuccess(DownloadManagerQuery.getFileName(mDm,mId));
                            }
                        }
                        break;
                    case UPDATE_STATUS:
                        updateDataAndStatus();
                        break;
                }
            } catch (Exception e) {
                Log.d("Task", "handleMessage-exception:" + e.getMessage());
            }
        }

    }

    /**
     * 接收下载完成广播
     */
    private static class CompleteReceiver extends BroadcastReceiver {

        private long mId;
        private Handler mHandler;

        public CompleteReceiver(Handler handler, long id) {
            mId = id;
            mHandler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * get the id of download which have download success, if the id is
             * my id and it's status is successful, then install it
             **/
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, INVALID_DOWNLOAD_ID);
            if (completeDownloadId != mId)
                return;
            mHandler.sendMessage(mHandler.obtainMessage(UPDATE_STATUS));
//            // 下载成功
//            if (DownloadManagerQuery.isDownloadSuccess(mDm, mId)) {
//                install(context, filePath);
//                isFileExist = true;
//            }
        }
    }

    @IntDef({TaskStatus.FAILED, TaskStatus.PENDING, TaskStatus.SUCCESS, TaskStatus.RUNNING, TaskStatus.PAUSED})
    @Retention(RetentionPolicy.CLASS)
    public @interface TaskStatus {

        /**
         * 下载失败
         */
        int FAILED = DownloadManager.STATUS_FAILED;

        /**
         * 等待下载中
         */
        int PENDING = DownloadManager.STATUS_PENDING;

        /**
         * 下载成功
         */
        int SUCCESS = DownloadManager.STATUS_SUCCESSFUL;

        /**
         * 下载中
         */
        int RUNNING = DownloadManager.STATUS_RUNNING;

        /**
         * 暂停
         */
        int PAUSED = DownloadManager.STATUS_PAUSED;

        /**
         * 错误任务：任务id不对
         */
        int ERROR_TASK = -404;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */
    public static boolean installApk(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.parse("file://" + filePath),
                    "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    /**
     * 回调
     */
    public interface OnDownloadListener {
        void onDownloadChanged(@TaskStatus int status, int total, int downloaded);

        void onDownloadSuccess(String filePath);
    }

    static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");
    public static final int MB_2_BYTE = 1024 * 1024;
    public static final int KB_2_BYTE = 1024;

    /**
     * 格式化文本显示
     *
     * @param size
     * @return
     */
    public static CharSequence getFormatSize(long size) {
        try {
            if (size <= 0) {
                return "0M";
            }

            if (size >= MB_2_BYTE) {
                return new StringBuilder(16)
                        .append(DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE))
                        .append("M");
            } else if (size >= KB_2_BYTE) {
                return new StringBuilder(16)
                        .append(DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE))
                        .append("K");
            } else {
                return size + "B";
            }
        } catch (Exception e) {
            return "0M";
        }

    }

    /**
     * 格式化百分比显示
     *
     * @param progress
     * @param max
     * @return
     */
    public static String getFormatPercent(long progress, long max) {
        try {
            int rate = 0;
            if (progress <= 0 || max <= 0) {
                rate = 0;
            } else if (progress > max) {
                rate = 100;
            } else {
                rate = (int) ((double) progress / max * 100);
            }
            return new StringBuilder(16).append(rate).append("%").toString();
        } catch (Exception e) {
            return "0%";
        }
    }


    /**
     * 下载请求参数
     */
    public static class DownloadRequestParams {
        /**
         * 下载地址
         */
        public String mUrl;

        /**
         * 保存的目录地址
         */
        public String mDir;

        /**
         * 文件名字
         */
        public String mFileName;

        /**
         * 显示标题
         */
        public String mTipTitle;

        /**
         * 描述
         */
        public String mDesc;

        /**
         * 是否只允许wifi下载
         */
        public boolean mOnlyWifi;

        public DownloadRequestParams() {
            mOnlyWifi = true;
        }


        /**
         * 创建请求
         *
         * @return
         */
        public DownloadManager.Request build() {
            File folder = Environment
                    .getExternalStoragePublicDirectory(mDir);
            if (!folder.exists() || !folder.isDirectory()) {
                if (!folder.mkdirs()) {
                    throw new RuntimeException("Can not make dir.");
                }
            }
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mUrl));
            request.setDestinationInExternalPublicDir(mDir, mFileName);
            request.setTitle(mTipTitle);
            request.setDescription(mDesc);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setVisibleInDownloadsUi(false);
            if (mOnlyWifi) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            } else {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                request.setAllowedOverRoaming(true);
            }
            request.allowScanningByMediaScanner();
            request.setMimeType(getMimeType(mFileName));
//            request.setMimeType("application/cn.trinea.download.file");
            return request;
        }


        /**
         * 返回mimetype
         *
         * @param extension 扩展名，可以传文件名字或者url地址
         * @return
         */
        private String getMimeType(String extension) {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        }
    }

}
