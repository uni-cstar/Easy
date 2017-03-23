package easy.app.page;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Lucio on 17/3/10.
 */

public class EasyPageView implements MultiStateView.PageView {


    protected Context mContext;
    protected View mContentView;
    @MultiStateView.PageViewType
    protected int mViewType;

    boolean fromXml, autoShowWhenCreate;
    int layoutId;

    /**
     * @param context            上下文
     * @param layoutId           视图文件ID
     * @param autoShowWhenCreate 创建时是否自动现实
     * @param type               视图类别
     */
    public EasyPageView(Context context, @LayoutRes int layoutId, boolean autoShowWhenCreate, @MultiStateView.PageViewType int type) {
        this.layoutId = layoutId;
        setValue(context, autoShowWhenCreate, type, true);
    }

    /**
     * @param view               视图
     * @param autoShowWhenCreate 创建时是否自动现实
     * @param type               视图类别
     */
    public EasyPageView(View view, boolean autoShowWhenCreate, @MultiStateView.PageViewType int type) {
        mContentView = view;
        setValue(view.getContext(), autoShowWhenCreate, type, false);
    }

    private void setValue(Context context, boolean autoShowWhenCreate, @MultiStateView.PageViewType int type, boolean fromXml) {
        mContext = context;
        mViewType = type;
        this.autoShowWhenCreate = autoShowWhenCreate;
        this.fromXml = fromXml;
    }

    @Override
    public boolean autoShowWhenFirstUsed() {
        return this.autoShowWhenCreate;
    }

    @Override
    public int getPageViewType() {
        return mViewType;
    }

    @Override
    public View getContentView() {
        if (mContentView == null && fromXml) {
            mContentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        }
        return mContentView;
    }

    @Override
    public void showPageView() {
        if (mContentView != null) {
            Animation ani = AnimationUtils.loadAnimation(mContext, android.support.v7.appcompat.R.anim.abc_fade_in);
            mContentView.startAnimation(ani);
            mContentView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hidePageView() {
        if (mContentView != null) {
            Animation ani = AnimationUtils.loadAnimation(mContext, android.support.v7.appcompat.R.anim.abc_fade_out);
            mContentView.startAnimation(ani);
            mContentView.setVisibility(View.GONE);
        }
    }
}
