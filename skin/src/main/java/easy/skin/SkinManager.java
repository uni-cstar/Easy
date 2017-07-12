package easy.skin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import easy.skin.attr.SkinAttrSupport;
import easy.skin.factory.SkinAttrFactory;
import easy.skin.impl.SkinChangedListener;
import easy.skin.impl.SkinCompatImpl;
import easy.skin.impl.SkinFontChangedListener;
import easy.skin.impl.SkinLoadListener;
//import easy.skin.util.SkinPrefUtil;
import easy.skin.util.SkinUtil;

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

    /**
     * 资源管理者（提供资源解析）
     */
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

    /**
     * 是否存在皮肤切换
     */
    private boolean mIsUseSkinPlugin, mIsInit;

    private boolean mEnableFontChange = false;

    /**
     * 是否在创建换肤View时执行对应的换肤属性
     * 用于解决某些属性在没有使用换肤时的兼容问题
     * 比如drawableTint 是api 23才支持的属性
     */
    private boolean mIsApplySkinAttrWhenCreateSkinView;

//
//    /**
//     * 皮肤 preferences，保存一些常量
//     */
//    SkinPrefUtil mPrefUtils;

    /**
     * 回调
     */
    private List<SkinChangedListener> mSkinChangedListeners;

    private List<SkinFontChangedListener> mSkinFontChangedListeners;

    private SkinManager() {
        mSkinCompat = new SkinCompatDef();
    }

    //singleton
    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        static final SkinManager instance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.instance;
    }

    private void checkInit() {
        if (!mIsInit)
            throw new RuntimeException("Please call the init method ");
    }

    /**
     * 获取资源管理器
     *
     * @return
     */
    public ResourceManager getResourceManager() {
        checkInit();
        return mResourceManager;
    }

    /**
     * 属性解析
     *
     * @return
     */
    public SkinAttrFactory getSkinAttrFactory() {
        checkInit();
        return mSkinAttrFactory;
    }

    /**
     * 设置属性解析工厂
     *
     * @param attrFactory
     */
    public void setSkinAttrFactory(SkinAttrFactory attrFactory) {
        mSkinAttrFactory = attrFactory;
    }

    /**
     * 设置资源未找到时是否忽略设置控件对应值
     *
     * @param ignore
     */
    public void setIgnoreWhenAttrNotFound(boolean ignore) {
        SkinAttrSupport.setIgnoreWhenAttrNotFound(ignore);
    }

    /**
     * 初始化
     * 建议在{@link Application#onCreate()}处进行调用
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
//        mSkinAttrFactory = SkinAttrFactory.createPrefixFactory(null);
        //默认使用命名空间解析方式
        mSkinAttrFactory = SkinAttrFactory.createNamespaceFactory();
        //默认资源管理器
        mResourceManager = new ResourceManager(mContext, mContext.getResources(), mContext.getPackageName(), "");
        //设置初始化标记
        mIsInit = true;

//        mPrefUtils = new SkinPrefUtil(mContext);
//        String skinPluginPath = mPrefUtils.getPluginPath();
//        if (SkinUtil.isNullOrEmpty(skinPluginPath))
//            return;
//        String skinPluginPkg = mPrefUtils.getPluginPkgName();
//        String suffix = mPrefUtils.getSuffix();
//
//        File file = new File(skinPluginPath);
//        if (!file.exists()) return;
//        try {
//            loadSkinPlugin(skinPluginPath, skinPluginPkg, suffix);
//        } catch (Exception e) {
//            mPrefUtils.clear();
//            e.printStackTrace();
//        }
    }

    /**
     * 是否允许字体切换
     *
     * @param enable
     */
    public void setEnableFontChange(boolean enable) {
        mEnableFontChange = enable;

    }

    /**
     * 切换字体
     * 如果当前使用了皮肤包，则从皮肤包中读取字体文件，反之则从程序内读取字体文件
     *
     * @param fontName
     */
    public void changeFont(String fontName) {
        changeFont(fontName, isUseSkin() ? FONT_EXTERNAL : FONT_INNER);
    }

    /**
     * 切换字体
     *
     * @param fontName 字体文件名
     * @param fontType 字体来源类型
     */
    public void changeFont(String fontName, @FontType int fontType) {
        if (fontType == FONT_EXTERNAL) {
            TypefaceUtils.loadTypeface(mContext, fontName, mResources.getAssets());
        } else {
            TypefaceUtils.loadTypeface(mContext, fontName, mContext.getAssets());
        }
        notifySkinFontChangedListeners();
    }

    /**
     * 设置为默认字体
     */
    public void restoreDefaultFont() {
        TypefaceUtils.restoreDefaultTypeface(mContext);
        notifySkinFontChangedListeners();
    }

    /**
     * 获取当前字体
     *
     * @return
     */
    public Typeface getCurrentTypeface() {
        return TypefaceUtils.getCurrentTypeface();
    }

    /**
     * 是否切换字体
     *
     * @return
     */
    public boolean isFontChangeEnable() {
        return mEnableFontChange;
    }

    /**
     * 程序内字体切换
     */
    public static final int FONT_INNER = 1;

    /**
     * 皮肤包字体切换
     */
    public static final int FONT_EXTERNAL = 2;

    @IntDef({FONT_INNER, FONT_EXTERNAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontType {

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
        mResourceManager = new ResourceManager(mContext, mResources, pkgName, suffix);
        updateSkinInfo(apkPath, pkgName, suffix);
    }

    /**
     * 加载皮肤插件
     *
     * @param apkPath 插件包所在路径
     * @throws Exception
     */
    private void loadSkinPlugin(String apkPath, String suffix) throws Exception {
        String packName = mSkinCompat.getPackageName(mContext, apkPath);
        loadSkinPlugin(apkPath, packName, suffix);
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
//        mPrefUtils.putPluginPath(mSkinPath);
//        mPrefUtils.putPluginPkgName(mSkinPackageName);
//        mPrefUtils.putPluginSuffix(mSkinSuffix);
    }

    /**
     * 清除插件数据
     */
    private void clearSkinInfo() {
        mSkinPath = null;
        mSkinPackageName = null;
        mSkinSuffix = null;
        mIsUseSkinPlugin = false;
//        mPrefUtils.clear();
    }

    /**
     * 是否使用皮肤
     * 是否使用插件或者更改了资源后缀
     *
     * @return
     */
    public boolean isUseSkin() {
        return mIsUseSkinPlugin || !SkinUtil.isNullOrEmpty(mSkinSuffix);
    }

    public boolean isApplySkinAttrWhenCreateSkinView() {
        return mIsApplySkinAttrWhenCreateSkinView;
    }

    public void setApplySkinAttrWhenCreateSkinView(boolean value) {
        mIsApplySkinAttrWhenCreateSkinView = value;
    }

    /**
     * 还原到默认的皮肤
     */
    public void restoreDefaultSkin() {
        checkInit();
        clearSkinInfo();
        mResources = mContext.getResources();
        mSkinPackageName = mContext.getPackageName();
        //重置资源管理器
        mResourceManager.update(mContext, mResources, mSkinPackageName, mSkinSuffix);
        notifySkinChangedListeners();
    }

    /**
     * 通过后缀切换皮肤
     *
     * @param suffix
     */
    public void changeSkin(String suffix) {
        checkInit();
        mSkinSuffix = suffix;
        mResourceManager.setSuffix(mSkinSuffix);
//        mPrefUtils.putPluginSuffix(mSkinSuffix);
        notifySkinChangedListeners();
    }

    /**
     * 加载皮肤
     *
     * @param skinPath 皮肤路径
     * @param listener
     */
    public void loadSkin(String skinPath, final SkinLoadListener listener) {
        checkInit();
        loadSkin(skinPath, "", listener);
    }

    /**
     * 本地加载Skin
     * <p>
     * eg:theme.skin
     * </p>
     *
     * @param skinPath 皮肤路径
     * @param suffix   资源后缀
     * @param listener 回调
     */
    public void loadSkin(String skinPath, @Nullable final String suffix, final SkinLoadListener listener) {
        checkInit();
        //路径为空
        if (SkinUtil.isNullOrEmpty(skinPath)) {
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
                        loadSkinPlugin(params[0], suffix);
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
        checkInit();
        if (mSkinChangedListeners == null) return;
        for (SkinChangedListener listener : mSkinChangedListeners) {
            listener.onSkinChanged();
        }
    }


    /**
     * 添加 字体切换 Listener
     *
     * @param listener
     */
    public void addSkinFontChangedListener(SkinFontChangedListener listener) {
        if (mSkinFontChangedListeners == null) {
            mSkinFontChangedListeners = new ArrayList<SkinFontChangedListener>();
        }

        //预防重复添加
        if (!mSkinFontChangedListeners.contains(listener)) {
            mSkinFontChangedListeners.add(listener);
        }
    }

    /**
     * 移除监听
     *
     * @param listener
     */
    public void removeSkinFontChangedListener(SkinFontChangedListener listener) {
        if (mSkinFontChangedListeners == null) return;
        mSkinFontChangedListeners.remove(listener);
    }

    /**
     * 通知字体切换
     */
    public void notifySkinFontChangedListeners() {
        checkInit();
        if (mSkinFontChangedListeners == null) return;
        for (SkinFontChangedListener listener : mSkinFontChangedListeners) {
            listener.onSkinFontChanged();
        }
    }

    public Resources getSkinResources() {
        return mResources;
    }
}
