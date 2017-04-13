package easy.skin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easy.skin.attr.SkinAttr;
import easy.skin.attr.SkinAttrSupport;
import easy.skin.impl.ISkinActivityDelegate;
import easy.skin.impl.SkinChangedListener;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/30.
 * Skin 委托管理,可以用在Activity管理界面皮肤切换。
 */
public class SkinDelegate implements LayoutInflaterFactory, SkinChangedListener, ISkinActivityDelegate {


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

            if (mIsSkinSwitchAnimAlways) {
                if (animation != null)
                    view.startAnimation(animation);
            }
            boolean changed = mSkinViewMap.get(view).apply();

            //根据属性是否改变来确定是否展示动画
            if (!mIsSkinSwitchAnimAlways && changed && animation != null) {
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
    private SkinView injectSkinView(View view, List<SkinAttr> skinAttrs) {
        if (SkinUtil.isNullOrEmpty(skinAttrs))
            return null;
        SkinView skinView = new SkinView(view, skinAttrs);
        mSkinViewMap.put(view, skinView);
        if (SkinManager.getInstance().isUseSkin()) {
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

}
