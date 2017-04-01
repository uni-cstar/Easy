package easy.skin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import easy.skin.factory.NamespaceSkinAttrFactory;
import easy.skin.factory.PrefixSkinAttrFactory;
import easy.skin.factory.SkinAttrFactory;
import easy.skin.impl.SkinChangedListener;
import easy.skin.impl.SkinCompatImpl;
import easy.skin.impl.SkinLoadListener;
import easy.skin.util.SkinPrefUtil;

/**
 * Created by Lucio on 17/3/30.
 * 主题切换
 * <p>
 * 1.在Application 的 onCreate方法中调用此方法进行初始化{@link #init(Context)}
 * 2.恢复默认皮肤{@link #restoreDefaultSkin()}
 * 3.应用皮肤{@link #loadSkin}
 * 4.
 * 皮肤切换监听
 * 添加{@link #addSkinChangedListener(SkinChangedListener)}
 * 移除{@link #removeSkinChangedListener(SkinChangedListener)}
 * 触发{@link #notifySkinChangedListeners()}
 * </p>
 */

public class SkinManager {

    private Context mContext;

    /**
     * 皮肤版本兼容实现类
     */
    SkinCompatImpl mSkinCompat;

    /**
     * 属性解析方式
     */
    SkinAttrFactory mSkinAttrFactory;
    /**
     * 资源
     */
    private Resources mResources;
    private ResourceManager mResourceManager;
    /**
     * 皮肤包名
     */
    private String mSkinPackageName;
    /**
     * 皮肤路径
     */
    private String mSkinPath;

    /**
     * 皮肤属性后缀
     */
    private String mSkinSuffix;

    private boolean mIsUseSkinPlugin;

    SkinPrefUtil mPrefUtils;


    private List<SkinChangedListener> mSkinChangedListeners;

    private SkinManager() {
        mSkinCompat = new SkinCompatDef();
        mSkinAttrFactory = PrefixSkinAttrFactory.create("skin");
    }

    //singleton
    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        static final SkinManager instance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 获取资源管理器
     *
     * @return
     */
    public ResourceManager getResourceManager() {
//        if (!mIsUseSkinPlugin) {
//            mResourceManager = new ResourceManager(mContext,mContext.getResources(), mContext.getPackageName(), mSkinSuffix);
//        }
        return mResourceManager;
    }

    /**
     * 属性解析
     *
     * @return
     */
    public SkinAttrFactory getSkinAttrFactory() {
        return mSkinAttrFactory;
    }

    /**
     * 设置属性解析工厂
     * @param attrFactory
     */
    public void setSkinAttrFactory(SkinAttrFactory attrFactory){
        mSkinAttrFactory = attrFactory;
    }
    /**
     * 初始化
     * 建议在{@link Application#onCreate()}处进行调用
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mResourceManager = new ResourceManager(mContext,mContext.getResources(), mContext.getPackageName(), "");
        mPrefUtils = new SkinPrefUtil(mContext);

        String skinPluginPath = mPrefUtils.getPluginPath();
        if (TextUtils.isEmpty(skinPluginPath))
            return;
        String skinPluginPkg = mPrefUtils.getPluginPkgName();
        String suffix = mPrefUtils.getSuffix();

        File file = new File(skinPluginPath);
        if (!file.exists()) return;
        try {
            loadSkinPlugin(skinPluginPath, skinPluginPkg, suffix);
        } catch (Exception e) {
            mPrefUtils.clear();
            e.printStackTrace();
        }
    }

    /**
     * 装载插件数据
     *
     * @param apkPath
     * @param pkgName
     * @param suffix  后缀
     * @throws Exception
     */
    private void loadSkinPlugin(String apkPath, String pkgName, String suffix) throws Exception {
        AssetManager assetManager = mSkinCompat.createAssetManager(apkPath);
        Resources superRes = mContext.getResources();
        mResources = mSkinCompat.createResources(assetManager, superRes);
        mResourceManager = new ResourceManager(mContext,mResources, pkgName, suffix);
        updateSkinInfo(apkPath, pkgName, suffix);
    }

    /**
     * 加载皮肤插件
     *
     * @param apkPath 插件包所在路径
     * @throws Exception
     */
    private void loadSkinPlugin(String apkPath,String suffix) throws Exception {
        String packName = mSkinCompat.getPackageName(mContext, apkPath);
        loadSkinPlugin(apkPath, packName,suffix);
    }

    /**
     * 更新插件数据
     *
     * @param path    插件路径
     * @param pkgName 插件包名
     * @param
     */
    private void updateSkinInfo(String path, String pkgName, String suffix) {
        mSkinPath = path;
        mSkinPackageName = pkgName;
        mSkinSuffix = suffix;
        mIsUseSkinPlugin = true;
        mPrefUtils.putPluginPath(mSkinPath);
        mPrefUtils.putPluginPkgName(mSkinPackageName);
        mPrefUtils.putPluginSuffix(mSkinSuffix);
    }

    /**
     * 清除插件数据
     */
    private void clearSkinInfo() {
        mSkinPath = null;
        mSkinPackageName = null;
        mSkinSuffix = null;
        mIsUseSkinPlugin = false;
        mPrefUtils.clear();
    }

    /**
     * 是否使用皮肤
     * 是否使用插件或者更改了资源后缀
     * @return
     */
    public boolean isUseSkin() {
        return mIsUseSkinPlugin || !TextUtils.isEmpty(mSkinSuffix);
    }

    /**
     * 还原到默认的皮肤
     */
    public void restoreDefaultSkin() {
        clearSkinInfo();
        mResources = mContext.getResources();
        mSkinPackageName = mContext.getPackageName();
        //重置资源管理器
        mResourceManager.update(mContext,mResources, mSkinPackageName, mSkinSuffix);
        notifySkinChangedListeners();
    }

    /**
     * 通过后缀切换皮肤
     *
     * @param suffix
     */
    public void changeSkin(String suffix) {
        mSkinSuffix = suffix;
        mResourceManager.setSuffix(mSkinSuffix);
        mPrefUtils.putPluginSuffix(mSkinSuffix);
        notifySkinChangedListeners();
    }

    /**
     * 加载皮肤
     * @param skinPath 皮肤路径
     * @param listener
     */
    public void loadSkin(String skinPath, final SkinLoadListener listener){
        loadSkin(skinPath,"",listener);
    }

    /**
     * 本地加载Skin
     * <p>
     * eg:theme.skin
     * </p>
     *
     * @param skinPath 皮肤路径
     * @param suffix 资源后缀
     * @param listener 回调
     */
    public void loadSkin(String skinPath, @Nullable final String suffix , final SkinLoadListener listener) {

        //路径为空
        if (TextUtils.isEmpty(skinPath)) {
            if (listener != null) {
                listener.onSkinLoadFailed(new IllegalArgumentException("skinPath can not be empty!"));
            }
            return;
        }

        //皮肤文件不存在
        File file = new File(skinPath);
        if (!file.exists()) {
            if (listener != null) {
                listener.onSkinLoadFailed(new IllegalArgumentException("the skin file is not exists!"));
            }
            return;
        }

        //路径与当前相同
        if (skinPath.equals(mSkinPath)) {
            if (listener != null) {
                listener.onSkinLoadSuccess();
            }
            return;
        }

        new AsyncTask<String, Void, Exception>() {

            protected void onPreExecute() {
                if (listener != null) {
                    listener.onSkinLoadStart();
                }
            }

            @Override
            protected Exception doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        loadSkinPlugin(params[0],suffix);
                        return null;
                    }
                    return new IllegalArgumentException("invalid arguments!");
                } catch (Exception e) {
                    e.printStackTrace();
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Exception result) {
                try {
                    if (result != null) {
                        if (listener != null) {
                            listener.onSkinLoadFailed(result);
                        }
                    } else {
                        notifySkinChangedListeners();
                        if (listener != null) {
                            listener.onSkinLoadSuccess();
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onSkinLoadFailed(e);
                    }
                    e.printStackTrace();
                }
            }
        }.execute(skinPath);
    }

    /**
     * 添加 皮肤切换 Listener
     *
     * @param listener
     */
    public void addSkinChangedListener(SkinChangedListener listener) {
        if (mSkinChangedListeners == null) {
            mSkinChangedListeners = new ArrayList<SkinChangedListener>();
        }

        //预防重复添加
        if (!mSkinChangedListeners.contains(listener)) {
            mSkinChangedListeners.add(listener);
        }
    }

    /**
     * 移除监听
     *
     * @param listener
     */
    public void removeSkinChangedListener(SkinChangedListener listener) {
        if (mSkinChangedListeners == null) return;
        mSkinChangedListeners.remove(listener);
    }

    /**
     * 通知皮肤切换
     */
    public void notifySkinChangedListeners() {
        if (mSkinChangedListeners == null) return;
        for (SkinChangedListener listener : mSkinChangedListeners) {
            listener.onSkinChanged();
        }
    }


}
