package easy.skin.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by Lucio on 17/3/30.
 * 皮肤可能涉及的版本兼容的方法
 */
public interface SkinCompatImpl {

    /**
     * 创建Resources
     *
     * @param assets
     * @param sysRes
     * @return
     */
    Resources createResources(AssetManager assets, Resources sysRes);

    /**
     * 创建 AssetManager
     *
     * @return
     * @throws Exception
     */
    AssetManager createAssetManager(String apkPath) throws Exception;

    /**
     * 获取包名
     *
     * @param context
     * @param apkPath
     * @return
     */
    String getPackageName(Context context, String apkPath);
}
