package easy.skin.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by Lucio on 17/3/30.
 */
public class SkinUtil {

    /**
     * 根据apk路径获取apk的包名
     *
     * @param apkPath apk路径
     */
    public static String getPackageName(Context context, String apkPath) {
        PackageManager mPm = context.getApplicationContext().getPackageManager();
        PackageInfo mInfo = mPm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (mInfo != null) {
            return mInfo.packageName;
        }
        return null;
    }

    public static boolean isNullOrEmpty(List sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
