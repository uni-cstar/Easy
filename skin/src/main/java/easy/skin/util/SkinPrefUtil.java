package easy.skin.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Lucio on 17/3/30.
 * Skin SharedPreferences 帮助类
 * 保存皮肤路径／包名
 */
public class SkinPrefUtil {

    private Context mContext;

    private static final String PREF_FILE_NAME = "skin_plugin_pref";
    private static final String KEY_PLUGIN_PATH = "key_plugin_path";
    private static final String KEY_PLUGIN_PKG = "key_plugin_pkg";
    private static final String KEY_PLUGIN_SUFFIX = "key_plugin_suffix";
    private static final String KEY_FONT_PATH = "key_font_path";

    public SkinPrefUtil(Context context) {
        this.mContext = context;
    }

    public String getPluginPath() {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_PLUGIN_PATH, "");
    }

    public String getPluginPkgName() {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_PLUGIN_PKG, "");
    }

    public void putPluginPath(String path) {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PLUGIN_PATH, path).apply();
    }

    public void putPluginPkgName(String pkgName) {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PLUGIN_PKG, pkgName).apply();
    }

    public String getSuffix() {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_PLUGIN_SUFFIX, "");
    }


    public void putPluginSuffix(String suffix) {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PLUGIN_SUFFIX, suffix).apply();
    }

    /**
     * 保存字体路径
     *
     * @param context
     * @param path
     */
    public static void putFontPath(Context context, String path) {
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_FONT_PATH, path).apply();
    }

    /**
     * 获取字体路径
     *
     * @param context
     * @return
     */
    public static String getFontPath(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_FONT_PATH, null);
    }

    public boolean clear() {
        SharedPreferences sp = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.edit().clear().commit();
    }


}
