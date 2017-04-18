package easy.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by SupLuo on 2016/2/25.
 */
public class ResourceUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取带单位大小对应的像素值
     *
     * @param context
     * @param unit    单位 例如：TypedValue.COMPLEX_UNIT_DIP
     * @param value   20
     * @return 20dip对应的值
     */
    public static float getUnitValue(Context context, int unit, float value) {
        return TypedValue.applyDimension(unit, value, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * 获取 layout 布局文件ID
     *
     * @param context Context
     * @param resName 布局文件名
     * @return layout
     */
    public static int getLayoutId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout",
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
        return context.getResources().getIdentifier(resName, "string",
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
        return context.getResources().getIdentifier(resName,
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
        return context.getResources().getIdentifier(resName,
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
        return context.getResources().getIdentifier(resName, "style",
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
        return context.getResources().getIdentifier(resName, "styleable",
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
        return context.getResources().getIdentifier(resName, "anim",
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
        return context.getResources().getIdentifier(resName, "id",
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
        return context.getResources().getIdentifier(resName, "color",
                context.getPackageName());
    }
}
