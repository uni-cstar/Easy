package easy.view.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import easy.R;
import easy.view.gesture.EasyGesture;

/**
 * Created by Lucio on 17/4/14.
 * 简易的底部Tab容器
 */
public class EasyTabHost extends LinearLayout {

    protected int mCurrentTab = -1;

    private List<IEasyTabItem> mTabItems = new ArrayList<>();

    /**
     * 用户监听回调
     */
    private OnEasyTabChangedListener mTabChangedListener;

    private OnEasyTabDoubleClickListener mTabDoubleClickListener;

    private EasyTabWidget mTabWidget;

    public EasyTabHost(Context context) {
        this(context, null);
    }

    public EasyTabHost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    public void setup() {
        mCurrentTab = -1;
        mTabItems.clear();
        if ((mTabWidget = (EasyTabWidget) findViewById(R.id.easyTabs)) == null) {
            mTabWidget = new EasyTabWidget(getContext());
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mTabWidget.setLayoutParams(lp);
            mTabWidget.setOrientation(HORIZONTAL);
            mTabWidget.setId(R.id.easyTabs);
            this.addView(mTabWidget);
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color
     */
    public void setDividerColor(int color) {
       mTabWidget.setDividerColor(color);
    }

    /**
     * 设置分割线粗细
     *
     * @param px
     */
    public void setDividerStroke(int px) {
        mTabWidget.setDividerStroke(px);
    }

    /**
     * 添加tab
     *
     * @param tabItem
     */
    public void addTab(IEasyTabItem tabItem) {
        View view = tabItem.getView();
        view.setClickable(true);
        view.setFocusableInTouchMode(true);
        EasyGesture.addTapGesture(view, new SimpleTabSelectListener(mSelectListener, mTabWidget.getChildCount()));
        setupTabView(tabItem);
        mTabWidget.addView(view);
        mTabItems.add(tabItem);
        if (mCurrentTab == -1) {
            setCurrentTab(0);
        }
    }

    //初始化tabview 参数
    private void setupTabView(IEasyTabItem tabItem) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        lp.weight = 1.0f;
        tabItem.getView().setLayoutParams(lp);
    }

    /**
     * 获取当前tab
     *
     * @return
     */
    public int getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * 设置当前tab
     *
     * @param index
     */
    public void setCurrentTab(int index) {
        mSelectListener.onEasyTabSelected(index);
    }

    /**
     * 设置单击回调
     *
     * @param listener
     */
    public void setOnEasyTabChangedListener(OnEasyTabChangedListener listener) {
        mTabChangedListener = listener;
    }

    /**
     * 设置双击回调
     *
     * @param listener
     */
    public void setOnEasyTabDoubleClickListener(OnEasyTabDoubleClickListener listener) {
        mTabDoubleClickListener = listener;
    }

    /**
     * select 处理
     */
    private OnEasyTabSelectListener mSelectListener = new OnEasyTabSelectListener() {
        @Override
        public void onEasyTabSelected(int index) {
            //不处理
            if (index == mCurrentTab || index < 0 || index >= mTabItems.size())
                return;

            //让之前的取消选中
            if (mCurrentTab >= 0 && mCurrentTab < mTabItems.size()) {
                mTabItems.get(mCurrentTab).onTabSelected(false);
            }

            mCurrentTab = index;
            mTabItems.get(mCurrentTab).onTabSelected(true);

            if (mTabChangedListener != null) {
                mTabChangedListener.onEasyTabSelected(mCurrentTab);
            }
        }

        @Override
        public void onEasyTabDoubleClick(int index) {
            if (mTabDoubleClickListener != null) {
                mTabDoubleClickListener.onEasyTabDoubleClick(index);
            }
        }

    };

    public interface OnEasyTabChangedListener {
        void onEasyTabSelected(int index);
    }

    public interface OnEasyTabDoubleClickListener {
        void onEasyTabDoubleClick(int index);
    }

    private interface OnEasyTabSelectListener extends OnEasyTabChangedListener, OnEasyTabDoubleClickListener {
    }


    /**
     * 过渡类，用于包装tab item的回调
     */
    private static class SimpleTabSelectListener implements EasyGesture.OnEasyTapListener {

        private OnEasyTabSelectListener mListener;
        private int mIndex;

        public SimpleTabSelectListener(OnEasyTabSelectListener listener, int index) {
            mListener = listener;
            mIndex = index;
        }

        @Override
        public boolean onEasySingleTaped() {
            if (mListener != null)
                mListener.onEasyTabSelected(mIndex);
            return true;
        }

        @Override
        public boolean onEasyDoubleTaped() {
            if (mListener != null)
                mListener.onEasyTabDoubleClick(mIndex);
            return false;
        }
    }

}
