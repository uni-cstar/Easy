package easy.skin.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhy.changeskin.constant.SkinConfig;

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
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(SkinConfig.KEY_PLUGIN_SUFFIX, "");
    }


    public void putPluginSuffix(String suffix) {
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SkinConfig.KEY_PLUGIN_SUFFIX, suffix).apply();
    }

    public boolean clear() {
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE);
        return sp.edit().clear().commit();
    }


}
