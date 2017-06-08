package easy.skin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import easy.skin.attr.SkinAttr;
import easy.skin.attr.SkinAttrSupport;
import easy.skin.impl.ISkinActivityDelegate;
import easy.skin.impl.SkinChangedListener;
import easy.skin.impl.SkinFontChangedListener;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/30.
 * Skin 委托管理,可以用在Activity管理界面皮肤切换。
 */
public class SkinDelegate implements LayoutInflaterFactory, SkinChangedListener, SkinFontChangedListener, ISkinActivityDelegate {


    private AppCompatActivity mActivity;

    private Map<SoftReference<View> , SkinView> mSkinViewMap = new HashMap<>();


    private FontRepository mFontRepository;

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

    private SkinChangedListener mSkinChangedListener;
    private SkinFontChangedListener mFontChangedListener;

    private SkinDelegate(AppCompatActivity context) {
        this(context, false);
    }

    private SkinDelegate(AppCompatActivity context, boolean enableStatusBarColor) {
        mActivity = context;
        mEnableStatusBarColor = enableStatusBarColor;
        mViewProducer = new ViewProducer();
        mEnableSkinSwitchAnim = true;//默认允许主题切换动画
        mIsSkinSwitchAnimAlways = false;//默认控件属性无变化时不执行动画
    }

    public static SkinDelegate create(AppCompatActivity activity) {
        return new SkinDelegate(activity);
    }

    public static SkinDelegate create(AppCompatActivity activity, boolean enableStatusBarColor) {
        return new SkinDelegate(activity, enableStatusBarColor);
    }

    /**
     * 在Activity调用super.onCrate(Bundle)之前调用此方法
     * {@link Activity#onCreate(Bundle)}
     */
    @Override
    public void beforeCallSuperOnCreate() {
        LayoutInflater layoutInflater = mActivity.getLayoutInflater();
        LayoutInflaterCompat.setFactory(layoutInflater, this);
        SkinManager.getInstance().addSkinChangedListener(this);
        SkinManager.getInstance().addSkinFontChangedListener(this);
    }

    /**
     * 在Activity调用super.onCrate(Bundle)之前调用此方法
     * {@link Activity#onCreate(Bundle)}
     */
    @Override
    public void afterCallSuperOnCreate() {
        changeStatusColor();
    }

    /**
     * {@link Activity#onDestroy()}
     */
    @Override
    public void onDestroy() {
        SkinManager.getInstance().removeSkinChangedListener(this);
        SkinManager.getInstance().removeSkinFontChangedListener(this);
    }

    /**
     * 设置皮肤切换监听
     *
     * @param skinChangeListener
     */
    @Override
    public void setOnSkinChangedListener(SkinChangedListener skinChangeListener) {
        mSkinChangedListener = skinChangeListener;
    }

    /**
     * 设置字体切换监听
     *
     * @param fontChangedListener
     */
    @Override
    public void setOnSkinFontChangedListener(SkinFontChangedListener fontChangedListener) {
        mFontChangedListener = fontChangedListener;
    }

    /**
     * 设置是否启用主题切换的动画
     * 默认开启
     *
     * @param enableSkinSwitchAnim
     */
    @Override
    public void setEnableSkinSwitchAnim(boolean enableSkinSwitchAnim) {
        this.mEnableSkinSwitchAnim = enableSkinSwitchAnim;
    }

    /**
     * 设置是否在view属性没有改变时是否也有动画切换
     * 默认关闭
     *
     * @param isSkinSwitchAnimAlways
     */
    @Override
    public void setIsSkinSwitchAnimAlways(boolean isSkinSwitchAnimAlways) {
        this.mIsSkinSwitchAnimAlways = isSkinSwitchAnimAlways;
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
        if (mSkinChangedListener != null)
            mSkinChangedListener.onSkinChanged();
    }

    @Override
    public void onSkinFontChanged() {
        applyFont();
        if (mFontChangedListener != null)
            mFontChangedListener.onSkinFontChanged();
    }

    private void applyFont() {
        if (mFontRepository == null)
            return;
        mFontRepository.applyFont(SkinManager.getInstance().getCurrentTypeface());
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

        Iterator<SoftReference<View>> iterator = mSkinViewMap.keySet().iterator();
        while (iterator.hasNext()){
            SoftReference<View> viewRef = iterator.next();
            View view = viewRef.get();
            if (view == null) {
                mSkinViewMap.remove(viewRef);
                iterator.remove();
                continue;
            }

            if (mIsSkinSwitchAnimAlways) {
                if (animation != null)
                    view.startAnimation(animation);
            }
            boolean changed = mSkinViewMap.get(viewRef).apply();

            //根据属性是否改变来确定是否展示动画
            if (!mIsSkinSwitchAnimAlways && changed && animation != null) {
                view.startAnimation(animation);
            }
        }

//        for (SoftReference<View> viewRef : mSkinViewMap.keySet()) {
//            View view = viewRef.get();
//            if (view == null) {
//                continue;
//            }
//
//            if (mIsSkinSwitchAnimAlways) {
//                if (animation != null)
//                    view.startAnimation(animation);
//            }
//            boolean changed = mSkinViewMap.get(viewRef).apply();
//
//            //根据属性是否改变来确定是否展示动画
//            if (!mIsSkinSwitchAnimAlways && changed && animation != null) {
//                view.startAnimation(animation);
//            }
//        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        try {
            List<SkinAttr> skinAttrs = SkinManager.getInstance().getSkinAttrFactory().getSkinAttrs(attrs, context);

            if (SkinUtil.isNullOrEmpty(skinAttrs)) {
                AppCompatDelegate delegate = mActivity.getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                if (view != null && view instanceof TextView) {
                    addFontChangeView((TextView) view);
                }
                return view;
            } else {
                View view = mViewProducer.createViewFromTag(context, name, attrs);
                if (view == null) {
                    return null;
                }

                if (view instanceof TextView) {
                    addFontChangeView((TextView) view);
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
    private SkinView injectSkinView(View view, List<SkinAttr> skinAttrs) {
        if (SkinUtil.isNullOrEmpty(skinAttrs))
            return null;
        SkinView skinView = new SkinView(view, skinAttrs);
        mSkinViewMap.put(new SoftReference<View>(view), skinView);
        if (SkinManager.getInstance().isApplySkinAttrWhenCreateSkinView() || SkinManager.getInstance().isUseSkin()) {
            skinView.apply();
        }
        return skinView;
    }


    /**
     * 移除View即其下的所有子view
     *
     * @param view
     */
    @Override
    public void removeAllSkinView(View view) {
        //移除view
        removeSkinView(view);
        //移除view下的子view
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllSkinView(viewGroup.getChildAt(i));
            }
        }
    }

    /**
     * 从viewmap中移除view
     */
    @Override
    public void removeSkinView(View view) {
        if (view == null)
            return;

        mSkinViewMap.remove(view);

        if ((view instanceof TextView)
                && SkinManager.getInstance().isFontChangeEnable()) {
            removeFontChangeView((TextView) view);
        }
    }

    @Override
    public void tryApplySkinView(View target) {
        Iterator<SoftReference<View>> iterator = mSkinViewMap.keySet().iterator();
        while (iterator.hasNext()){
            SoftReference<View> viewRef = iterator.next();
            View view = viewRef.get();
            if (view == null) {
                mSkinViewMap.remove(viewRef);
                iterator.remove();
                continue;
            }

            if(view == target){
                mSkinViewMap.get(viewRef).apply();
            }
        }
    }

    /**
     * 添加换肤View
     *
     * @param view
     * @param skinAttrs 换肤属性 {@link SkinAttrSupport#genSkinAttr(String, String, String)}
     */
    @Override
    public SkinView addSkinView(View view, List<SkinAttr> skinAttrs) {
        return injectSkinView(view, skinAttrs);
    }

    @Override
    public SkinView addSkinView(View view, String attrName, String resEntryName, String resTypeName) {
        SkinAttr skinAttr = SkinAttrSupport.genSkinAttr(attrName, resEntryName, resTypeName);
        List<SkinAttr> skinAttrs = Collections.singletonList(skinAttr);
        return addSkinView(view, skinAttrs);
    }

    @Override
    public void addFontChangeView(TextView textView) {
        if (!SkinManager.getInstance().isFontChangeEnable())
            return;
        if (mFontRepository == null) {
            mFontRepository = new FontRepository();
        }
        mFontRepository.add(textView);
    }

    @Override
    public void removeFontChangeView(TextView textView) {
        if (mFontRepository == null) {
            return;
        }
        mFontRepository.remove(textView);
    }


}
