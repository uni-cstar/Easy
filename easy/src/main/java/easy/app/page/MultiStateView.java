package easy.app.page;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

import easy.R;

/**
 * Created by Lucio on 17/3/21.
 */

public class MultiStateView extends FrameLayout {

    HashMap<Integer, PageView> mChildViews = new HashMap<Integer, PageView>();

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void inflateViews(Context context, AttributeSet attrs, int defStyleAttr) {
        // Custom attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        if (a.hasValue(R.styleable.MultiStateView_contentLayout)) {
            int contentResId = a.getResourceId(R.styleable.MultiStateView_contentLayout, 0);
            boolean autoLoad = a.getBoolean(R.styleable.MultiStateView_autoLoadContentLayout, true);
//            mChildViews.put() createPageView().
        }

        if (a.hasValue(R.styleable.MultiStateView_loadingLayout)) {

        }

        if (a.hasValue(R.styleable.MultiStateView_emptyLayout)) {

        }

        if (a.hasValue(R.styleable.MultiStateView_errorLayout)) {

        }

    }


    /**
     * 创建pageview
     *
     * @param context  上下文
     * @param layoutId 布局文件id
     * @param autoShow 是否自动显示
     * @param viewId   视图的唯一主键id  缺省值：{@link PageViewType}
     * @return
     */
    public static PageView createPageView(Context context, @LayoutRes int layoutId, boolean autoShow, int viewId) {
        return new EasyPageView(context, layoutId, autoShow, viewId);
    }

    /**
     * 创建pageView
     *
     * @param view     视图
     * @param autoShow 是否自动显示
     * @param viewId   视图的唯一主键id  缺省值：{@link PageViewType}
     * @return
     */
    public static PageView createPageView(View view, boolean autoShow, int viewId) {
        return new EasyPageView(view, autoShow, viewId);
    }

    /**
     * 添加PageView
     *
     * @param pageView
     */
    public void addPageView(PageView pageView) {
        int type = pageView.getPageViewType();
        PageView cache = mChildViews.get(type);
        if (cache != null)
            throw new RuntimeException("不能重复添加。");

        if (pageView.autoShowWhenFirstUsed()) {
            showShowPageView(pageView);
        }
        mChildViews.put(type, pageView);
    }

    /**
     * 显示
     *
     * @param type       视图类别
     * @param hideOthers 是否隐藏其他类别视图
     */
    public void showPageViewView(@PageViewType int type, boolean hideOthers) {
        //隐藏其他的视图
        if (hideOthers) {
            hideAllPageView();
        }

        //显示当前类别视图
        PageView pageView = mChildViews.get(type);
        if (pageView == null)
            return;
        showShowPageView(pageView);
    }

    /**
     * 显示PageView(显示的唯一入口方法)
     *
     * @param pageView
     */
    private void showShowPageView(PageView pageView) {
        if (pageView == null)
            return;
        View contentView = pageView.getContentView();
        if (contentView != null) {//子page是一个view
            ViewParent parent = contentView.getParent();
            if (parent == null) {//添加到父结点
                this.addView(contentView);
            } else if (parent != this) {//父结点不同，不能操作
                throw new RuntimeException("the parent view group of the page view is not current");
            }
        }
        pageView.showPageView();
    }

    /**
     * 隐藏视图
     *
     * @param type 视图类别
     */
    public void hidePageView(@PageViewType int type) {
        //当前类别视图
        PageView pageView = mChildViews.get(type);
        if (pageView == null)
            return;
        pageView.hidePageView();
    }

    /**
     * 隐藏已经添加的所有视图
     */
    public void hideAllPageView() {
        for (PageView item : mChildViews.values()) {
            item.hidePageView();
        }
    }

    /**
     * 移除视图
     *
     * @param type
     */
    public void removePageView(@PageViewType int type) {
        PageView pageView = mChildViews.get(type);
        if (pageView == null)
            return;
        View view = pageView.getContentView();
        if (view != null) {
            this.removeView(view);
        }
        mChildViews.remove(type);
    }

    /**
     * 移除所有视图
     */
    public void removeAllPageView() {
        this.removeAllViews();
        mChildViews.clear();
    }

    /**
     * 缺省的View状态
     */
    @IntDef({PageViewType.NONE, PageViewType.CONTENT, PageViewType.LOADING, PageViewType.EMPTY, PageViewType.ERROR,PageViewType.NO_RESULT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageViewType {
        /**
         * 无视图
         */
        int NONE = -1;
        /**
         * 内容视图
         */
        int CONTENT = 0;
        /**
         * 加载中视图
         */
        int LOADING = 1;
        /**
         * 空内容视图
         */
        int EMPTY = 2;
        /**
         * 错误视图
         */
        int ERROR = 3;

        /**
         * 没有结果
         */
        int NO_RESULT = 4;
    }

    /**
     * PageView接口
     */
    public interface PageView {

        /**
         * 创建的时候是否自动添加到视图显示
         *
         * @return
         */
        boolean autoShowWhenFirstUsed();

        /**
         * 视图类型
         *
         * @return
         */
        @PageViewType
        int getPageViewType();

        /**
         * 获取视图
         *
         * @return 视图对象
         */
        View getContentView();

        /**
         * 显示视图
         */
        void showPageView();

        /**
         * 隐藏视图
         */
        void hidePageView();
    }

    /**
     * 参数
     */
    public static class Params {

        private boolean isAutoLoad = false;
        private int width = LayoutParams.MATCH_PARENT, height = LayoutParams.MATCH_PARENT;

        public boolean isAutoLoad() {
            return isAutoLoad;
        }

        public void setAutoLoad(boolean autoLoad) {
            isAutoLoad = autoLoad;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

}
