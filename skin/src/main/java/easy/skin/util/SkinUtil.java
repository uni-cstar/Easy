package easy.skin.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.AnyRes;

import java.util.List;

import easy.skin.SkinManager;

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

    /**
     * 获取资源类型名字
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getResourceTypeName(Context context, @AnyRes int resId) {
        return context.getResources().getResourceTypeName(resId);
    }

    /**
     * 获取资源名字
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getResourceEntryName(Context context, @AnyRes int resId) {
        return context.getResources().getResourceEntryName(resId);
    }

    public static boolean isNullOrEmpty(List sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static boolean isNullOrEmpty(String content) {
        return content == null || content.length() == 0;
    }

    /**
     * 获取 layout 布局文件ID
     *
     * @param context Context
     * @param resName 布局文件名
     * @return layout
     */
    public static int getLayoutId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "layout",
                context.getPackageName());
    }

    /**
     * 获取 string 对应的ID
     *
     * @param context Context
     * @param resName string name的名称
     * @return string
     */
    public static int getStringId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "string",
                context.getPackageName());
    }

    /**
     * 获取 drawable资源ID
     *
     * @param context Context
     * @param resName drawable 的名称
     * @return drawable
     */
    public static int getDrawableId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName,
                "drawable", context.getPackageName());
    }

    /**
     * 获取 mipmap资源ID
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getMipmapId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName,
                "mipmap", context.getPackageName());
    }


    /**
     * 获取 style 资源ID
     *
     * @param context Context
     * @param resName style的名称
     * @return style
     */
    public static int getStyleId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "style",
                context.getPackageName());
    }

    /**
     * 获取 styleable资源ID
     *
     * @param context Context
     * @param resName styleable 的名称
     * @return styleable
     */
    public static Object getStyleableId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "styleable",
                context.getPackageName());
    }


    /**
     * 获取 anim 资源ID
     *
     * @param context Context
     * @param resName anim xml 文件名称
     * @return anim
     */
    public static int getAnimId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "anim",
                context.getPackageName());
    }

    /**
     * 获取 id 资源ID
     *
     * @param context Context
     * @param resName id 的名称
     * @return
     */
    public static int getId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "id",
                context.getPackageName());
    }

    /**
     * color
     *
     * @param context Context
     * @param resName color 名称
     * @return
     */
    public static int getColorId(Context context, String resName) {
        return SkinManager.getInstance().getSkinResources().getIdentifier(resName, "color",
                context.getPackageName());
    }
}
