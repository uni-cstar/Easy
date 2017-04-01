package easy.skin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easy.skin.attr.SkinAttr;
import easy.skin.impl.SkinChangedListener;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/30.
 * Skin 委托管理,可以用在Activity管理界面皮肤切换。
 */
public class SkinDelegate implements LayoutInflaterFactory, SkinChangedListener {


    private AppCompatActivity mActivity;

    private Map<View, SkinView> mSkinViewMap = new HashMap<>();

    /**
     * 是否允许设置状态栏颜色
     */
    private boolean mEnableStatusBarColor;

    /**
     * 是否允许主题切换时的动画
     */
    private boolean mEnableSkinSwitchAnim;

    /**
     * 是否总是有切换动画
     * true：总是有切换动画
     * false:如果控件属性没有更改，则不进行动画
     */
    private boolean mIsSkinSwitchAnimAlways;

    private ViewProducer mViewProducer;

    public SkinDelegate(AppCompatActivity context) {
        this(context, false);
    }

    public SkinDelegate(AppCompatActivity context, boolean enableStatusBarColor) {
        mActivity = context;
        mEnableStatusBarColor = enableStatusBarColor;
        mViewProducer = new ViewProducer();
        mEnableSkinSwitchAnim = true;//默认允许主题切换动画
        mIsSkinSwitchAnimAlways = false;//默认控件属性无变化时不执行动画
    }

    /**
     * 在Activity调用super.onCrate(Bundle)之前调用此方法
     * {@link Activity#onCreate(Bundle)}
     */
    public void beforeCallSuperOnCreate() {
        LayoutInflater layoutInflater = mActivity.getLayoutInflater();
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        SkinManager.getInstance().addSkinChangedListener(this);
    }

    /**
     * 在Activity调用super.onCrate(Bundle)之前调用此方法
     * {@link Activity#onCreate(Bundle)}
     */
    public void afterCallSuperOnCreate() {
        changeStatusColor();
    }

    /**
     * {@link Activity#onDestroy()}
     */
    public void onDestroy() {
        SkinManager.getInstance().removeSkinChangedListener(this);
    }

    /**
     * 设置是否启用主题切换的动画
     * 默认开启
     * @param mEnableSkinSwitchAnim
     */
    public void setEnableSkinSwitchAnim(boolean mEnableSkinSwitchAnim) {
        this.mEnableSkinSwitchAnim = mEnableSkinSwitchAnim;
    }

    /**
     * 设置是否在view属性没有改变时是否也有动画切换
     * 默认关闭
     * @param mIsSkinSwitchAnimAlways
     */
    public void setIsSkinSwitchAnimAlways(boolean mIsSkinSwitchAnimAlways) {
        this.mIsSkinSwitchAnimAlways = mIsSkinSwitchAnimAlways;
    }

    /**
     * 更改状态栏颜色
     */
    private void changeStatusColor() {
        if (!mEnableStatusBarColor)
            return;
        //// TODO: 17/3/31  change status bar color
    }

    /**
     * 皮肤切换回调
     */
    @Override
    public void onSkinChanged() {
        applySkin();
        changeStatusColor();
    }

    /**
     * 应用皮肤
     */
    private void applySkin() {
        if (mSkinViewMap.isEmpty()) {
            return;
        }
        Animation animation = null;
        if (mEnableSkinSwitchAnim) {
            animation = new AlphaAnimation(0.5f, 1.0f);
            animation.setDuration(500);
        }
        for (View view : mSkinViewMap.keySet()) {
            if (view == null) {
                continue;
            }

            if(mIsSkinSwitchAnimAlways){
                if (animation != null)
                    view.startAnimation(animation);
            }
            boolean changed = mSkinViewMap.get(view).apply();

            //根据属性是否改变来确定是否展示动画
            if(!mIsSkinSwitchAnimAlways && changed && animation != null){
                view.startAnimation(animation);
            }
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        try {
            List<SkinAttr> skinAttrs = SkinManager.getInstance().getSkinAttrFactory().getSkinAttrs(attrs, context);
            if (SkinUtil.isNullOrEmpty(skinAttrs)) {
//            AppCompatDelegate delegate = mActivity.getDelegate();
//            return delegate.tryCreateView(parent, name, context, attrs);
                return null;
            } else {
                View view = mViewProducer.createViewFromTag(context, name, attrs);
                if (view == null) {
                    return null;
                }
                injectSkinView(view, skinAttrs);
                return view;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 注册SkinView
     *
     * @param view
     * @param skinAttrs
     */
    private void injectSkinView(View view, List<SkinAttr> skinAttrs) {
        SkinView skinView = new SkinView(view, skinAttrs);
        mSkinViewMap.put(view, skinView);
        if (SkinManager.getInstance().isUseSkin()) {
            skinView.apply();
        }
    }

    /**
     * 创建View
     */
    private static class ViewProducer {
        private final Object[] mConstructorArgs = new Object[2];
        private final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();
        private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        private static final String[] sClassPrefixList = {
                "android.widget.",
                "android.view.",
                "android.webkit."
        };

        View createViewFromTag(Context context, String name, AttributeSet attrs) {
            if ("view".equals(name)) {
                name = attrs.getAttributeValue(null, "class");
            }

            try {
                mConstructorArgs[0] = context;
                mConstructorArgs[1] = attrs;

                if (-1 == name.indexOf('.')) {
                    for (String classPrefix : sClassPrefixList) {
                        View view = tryCreateView(context, name, classPrefix);
                        if (view != null) {
                            return view;
                        }
                    }
                    return null;
                } else {
                    return tryCreateView(context, name, null);
                }
            } catch (Exception e) {
                // We do not want to catch these, lets return null and let the actual LayoutInflater try
                return null;
            } finally {
                // Don't retain references on context.
                mConstructorArgs[0] = null;
                mConstructorArgs[1] = null;
            }
        }

        private View tryCreateView(Context context, String name, String prefix) {
            try {
                Constructor<? extends View> constructor = sConstructorMap.get(name);

                if (constructor == null) {
                    // Class not found in the cache, see if it's real, and try to add it
                    Class<? extends View> clazz = context.getClassLoader().loadClass(
                            prefix != null ? (prefix + name) : name).asSubclass(View.class);

                    constructor = clazz.getConstructor(sConstructorSignature);
                    sConstructorMap.put(name, constructor);
                }
                constructor.setAccessible(true);
                return constructor.newInstance(mConstructorArgs);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
