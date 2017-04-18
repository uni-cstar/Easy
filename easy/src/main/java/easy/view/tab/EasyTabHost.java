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

    private List<EasyTabItem> mTabItems = new ArrayList<>();

    /**
     * 用户监听回调
     */
    private OnEasyTabSelectListener mCustomTabSelectListener;

    private Paint mDividerPaint;
    private int mDividerWidth = 1;

    public EasyTabHost(Context context) {
        this(context, null);
    }

    public EasyTabHost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDividerPaint = new TextPaint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(mDividerWidth);
        mDividerPaint.setColor(context.getResources().getColor(R.color.easy_tab_host_divider));
    }

    /**
     * 设置分割线颜色
     *
     * @param color
     */
    public void setDividerColor(int color) {
        mDividerPaint.setColor(color);
        this.postInvalidate(0,0,this.getWidth(),mDividerWidth);
    }

    /**
     * 设置分割线粗细
     * @param px
     */
    public void setDividerStroke(int px) {
        mDividerWidth = px;
        mDividerPaint.setStrokeWidth(px);
        this.postInvalidate(0,0,this.getWidth(),mDividerWidth);
    }

    /**
     * 初始化
     */
    public void setup() {
        mCurrentTab = -1;
        int count = this.getChildCount();
        if (count == 0)
            return;
        List<EasyTabItem> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Object object = this.getChildAt(i);
            if (object instanceof EasyTabItem) {
                items.add((EasyTabItem) object);
            } else {
                throw new RuntimeException("child view must implements EasyTabItem");
            }
        }
        setup(items, 0);
    }

    /**
     * 初始化
     * @param tabItems
     * @param index
     */
    public void setup(List<EasyTabItem> tabItems, int index) {
        mCurrentTab = -1;
        mTabItems.clear();
        mTabItems.addAll(tabItems);
        this.removeAllViews();

        for (int i = 0; i < mTabItems.size(); i++) {
            addTabView(i, mTabItems.get(i));
        }

        if (index >= 0 && index < mTabItems.size()) {
            mSelectListener.onEasyTabSelected(index);
        } else {
            mSelectListener.onEasyTabSelected(0);
        }
    }


    /**
     * 添加tab view
     * @param index
     * @param tabItem
     */
    private void addTabView(final int index, EasyTabItem tabItem) {
        View view = tabItem.getView();
        view.setClickable(true);
        view.setFocusableInTouchMode(true);
        EasyGesture.addTapGesture(view, new SimpleTabSelectListener(mSelectListener, index));
        setupTabView(index, tabItem);
        this.addView(view, index);
    }

    /**
     * 初始化tabview
     * @param index
     * @param tabItem
     */
    private void setupTabView(int index, EasyTabItem tabItem) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        lp.weight = 1.0f;
        tabItem.getView().setLayoutParams(lp);
    }

    /**
     * 获取当前选中的tab
     * @return
     */
    public int getCurrentTabTag() {
        return mCurrentTab;
    }

    /**
     * 设置回调
     * @param listener
     */
    public void setOnEasyTabSelectListener(OnEasyTabSelectListener listener) {
        mCustomTabSelectListener = listener;
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

            if (mCustomTabSelectListener != null) {
                mCustomTabSelectListener.onEasyTabSelected(mCurrentTab);
            }
        }

        @Override
        public void onEasyTabDoubleClick(int index) {
            if (mCustomTabSelectListener != null) {
                mCustomTabSelectListener.onEasyTabDoubleClick(index);
            }
        }

    };


    public interface OnEasyTabSelectListener {
        void onEasyTabSelected(int index);

        void onEasyTabDoubleClick(int index);
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //绘制分割线
        if (this.getWidth() > 0)
            canvas.drawLine(0, 0, this.getWidth(), 0, mDividerPaint);
    }
}
