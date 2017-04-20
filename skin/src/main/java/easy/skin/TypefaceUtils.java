package easy.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;

import java.io.File;

import easy.skin.util.SkinPrefUtil;
import easy.skin.util.SkinUtil;

public class TypefaceUtils {

    private static Typeface CURRENT_TYPEFACE;

    /**
     * 获取当前字体
     *
     * @return
     */
    public static Typeface getCurrentTypeface() {
        if (CURRENT_TYPEFACE == null) {
            CURRENT_TYPEFACE = Typeface.DEFAULT;
        }
        return CURRENT_TYPEFACE;
    }

    /**
     * 使用默认字体
     * @param context
     */
    public static void restoreDefaultTypeface(Context context){
        CURRENT_TYPEFACE = Typeface.DEFAULT;
        SkinPrefUtil.putFontPath(context, "");
    }
    /**
     * 加载字体
     *
     * @param context
     * @param fontName
     * @return
     */
    public static Typeface loadTypeface(Context context, String fontName, AssetManager assetManager) {
        Typeface tf;
        if (!TextUtils.isEmpty(fontName)) {
            String fontPath = getFontPath(fontName);
            tf = Typeface.createFromAsset(assetManager, fontPath);
            SkinPrefUtil.putFontPath(context, fontPath);
        } else {
            tf = Typeface.DEFAULT;
            SkinPrefUtil.putFontPath(context, "");
        }
        TypefaceUtils.CURRENT_TYPEFACE = tf;
        return tf;
    }

    public static Typeface getTypeface(Context context,AssetManager assetManager) {
        try {
            String fontPath = SkinPrefUtil.getFontPath(context);
            Typeface tf;
            if (!SkinUtil.isNullOrEmpty(fontPath)) {
                tf = Typeface.createFromAsset(assetManager, fontPath);
            } else {
                tf = Typeface.DEFAULT;
                SkinPrefUtil.putFontPath(context, "");
            }
            return tf;
        } catch (Exception e) {
            e.printStackTrace();
            SkinPrefUtil.putFontPath(context, "");
            return Typeface.DEFAULT;
        }
    }

    private static String getFontPath(String fontName) {
        return SkinConst.FONT_DIR + File.separator + fontName;
    }
}
