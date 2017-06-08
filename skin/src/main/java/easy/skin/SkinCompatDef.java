package easy.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

import easy.skin.impl.SkinCompatImpl;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/30.
 * Skin 版本兼容缺省实现
 */

public class SkinCompatDef implements SkinCompatImpl {

    @Override
    public Resources createResources(AssetManager assets, Resources sysRes) {
        return new Resources(assets, sysRes.getDisplayMetrics(), sysRes.getConfiguration());
    }

    @Override
    public AssetManager createAssetManager(String apkPath) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();

        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, apkPath);

        return assetManager;
    }

    @Override
    public String getPackageName(Context context, String apkPath) {
        return SkinUtil.getPackageName(context, apkPath);
    }
}
