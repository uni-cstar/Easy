package easy.app.download;

import android.app.DownloadManager;
import android.database.Cursor;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by Lucio on 17/3/23.
 * DownloadManager工具函数
 */

public class DownloadManagerQuery {

    /**
     * 下载文件地址  > api 11
     **/
    private static final String COLUMN_LOCAL_FILENAME = "local_filename";
    /**
     * 下载文件地址 < api 11
     **/
    private static final String COLUMN_LOCAL_URI = "local_uri";

    /**
     * 获取下载文件地址的数据库列名
     *
     * @return
     */
    public static String getDownloadFilePathColumnName() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? COLUMN_LOCAL_URI
                : COLUMN_LOCAL_FILENAME;
    }

    /**
     * 暂停下载
     *
     * @param dm
     * @param ids 任务ids
     * @throws Exception 不支持暂停方法
     */
    public static void pauseDownload(DownloadManager dm, long... ids) throws Exception {
        if (ids == null || ids.length == 0)
            return;

        Method pauseMethod = DownloadManager.class.getMethod("pauseDownload", long[].class);
        pauseMethod.invoke(dm, ids);
    }

    /**
     * 重新下载任务
     *
     * @param dm
     * @param ids 任务ids
     * @throws Exception 不支持此方法
     */
    public static void resumeDownload(DownloadManager dm, long... ids) throws Exception {
        if (ids == null || ids.length == 0)
            return;
        Method resumeMethod = DownloadManager.class.getMethod("resumeDownload", long[].class);
        resumeMethod.invoke(dm, ids);
    }

    /**
     * 查询任务的进度和状态
     *
     * @param downloadManager
     * @param downloadId
     * @return int[0]:已下载大小  int[1]:总大小  int[2]:任务状态
     */
    public static int[] getBytesAndStatus(DownloadManager downloadManager, long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    /**
     * 是否下载成功
     *
     * @param dm
     * @param downloadId
     * @return
     */
    public static boolean isDownloadSuccess(DownloadManager dm, long downloadId) {
        return getStatusById(dm, downloadId) == DownloadManager.STATUS_SUCCESSFUL;
    }

    /**
     * 获取下载状态
     *
     * @param dm
     * @param downloadId
     * @return
     */
    public static int getStatusById(DownloadManager dm, long downloadId) {
        return getInt(dm, downloadId, DownloadManager.COLUMN_STATUS);
    }

    /**
     * 获取一列int值
     *
     * @param downloadId
     * @param columnName 列名
     * @return
     */
    private static int getInt(DownloadManager dm, long downloadId, String columnName) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        int result = -1;
        Cursor c = null;
        try {
            c = dm.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getInt(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }


    /**
     * 获取一列string 值
     *
     * @param downloadId
     * @param columnName
     * @return
     */
    private static String getString(DownloadManager dm, long downloadId, String columnName) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        String result = null;
        Cursor c = null;
        try {
            c = dm.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getString(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    /**
     * 获取下载文件名字
     *
     * @param dm
     * @param downloadId
     * @return
     */
    public static String getFileName(DownloadManager dm, long downloadId) {
        return getString(dm, downloadId, getDownloadFilePathColumnName());
    }

    /**
     * 获取Uri
     *
     * @param dm
     * @param downloadId
     * @return
     */
    public static String getUri(DownloadManager dm, long downloadId) {
        return getString(dm, downloadId, DownloadManager.COLUMN_URI);
    }

    /**
     * get failed code or paused reason
     *
     * @param downloadId
     * @return <ul>
     * <li>if status of downloadId is {@link DownloadManager#STATUS_PAUSED}, return
     * {@link #getPausedReason(DownloadManager, long)}}</li>
     * <li>if status of downloadId is {@link DownloadManager#STATUS_FAILED}, return {@link #getErrorCode(DownloadManager, long)}</li>
     * <li>if status of downloadId is neither {@link DownloadManager#STATUS_PAUSED} nor
     * {@link DownloadManager#STATUS_FAILED}, return 0</li>
     * </ul>
     */
    public int getReason(DownloadManager dm, long downloadId) {
        return getInt(dm, downloadId, DownloadManager.COLUMN_REASON);
    }

    /**
     * get paused reason
     *
     * @param dm
     * @param downloadId
     * @return <ul>
     * <li>if status of downloadId is {@link DownloadManager#STATUS_PAUSED}, return one of
     * {@link DownloadManager#PAUSED_WAITING_TO_RETRY}<br/>
     * {@link DownloadManager#PAUSED_WAITING_FOR_NETWORK}<br/>
     * {@link DownloadManager#PAUSED_QUEUED_FOR_WIFI}<br/>
     * {@link DownloadManager#PAUSED_UNKNOWN}</li>
     * <li>else return {@link DownloadManager#PAUSED_UNKNOWN}</li>
     * </ul>
     */
    public int getPausedReason(DownloadManager dm, long downloadId) {
        return getInt(dm, downloadId, DownloadManager.COLUMN_REASON);
    }

    /**
     * get failed error code
     *
     * @param downloadId
     * @return one of DownloadManager#ERROR_xx
     */
    public int getErrorCode(DownloadManager dm, long downloadId) {
        return getInt(dm, downloadId, DownloadManager.COLUMN_REASON);
    }
}
