package easy.view.tab;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import easy.R;
import easy.utils.ResourceUtil;
import ms.badge.HorizontalGravity;
import ms.badge.VerticalGravity;
import ms.badge.view.BadgeRelativeLayout;

/**
 * Created by Lucio on 17/4/14.
 * 市面上常见底部tab item的实现。
 * 实现了未读数红圈数字，以及拖动销毁等：借助https://github.com/bingoogolapple/BGABadgeView-Android 实现
 * 建议实现方式是创建EasyTabView的子类，在里面设置ImageView 和TextView的相关资源值
 */
public class EasyTabView extends BadgeRelativeLayout implements EasyTabItem {

    Context mContext;
    ImageView mImageView;
    //    BadgeRelativeLayout mBgaWrapper;
    TextView mTextView;
    int mTextSizeRes = R.dimen.size_tab_text;
    int mImageSizeRes = R.dimen.size_tab_image;
    int mMarginRes = R.dimen.size_tab_image_margin;

    public EasyTabView(Context context) {
        this(context, null);
    }

    public EasyTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setup(attrs);
    }

    protected void setup(AttributeSet attrs) {
        //设置badge位置
        this.getBadgeConfigHelper().setBadgeHorizontalGravity(HorizontalGravity.CENTER_START);
        this.getBadgeConfigHelper().setBadgeVerticalGravity(VerticalGravity.TOP);

        int textSize = mContext.getResources().getDimensionPixelSize(mTextSizeRes);
        int margin = mContext.getResources().getDimensionPixelSize(mMarginRes);
        int imgSize = mContext.getResources().getDimensionPixelSize(mImageSizeRes);

        //添加ImageView
        mImageView = new ImageView(mContext, attrs);
        mImageView.setId(R.id.easy_tab_id_image);
        LayoutParams imgLp = new LayoutParams(imgSize, imgSize);
        imgLp.addRule(CENTER_HORIZONTAL);
        imgLp.setMargins(margin, margin, margin, margin);
        mImageView.setLayoutParams(imgLp);
        this.addView(mImageView);

        //添加TextView
        mTextView = new TextView(mContext, attrs);
        mTextView.setId(R.id.easy_tab_id_text);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        LayoutParams tvlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tvlp.addRule(BELOW, R.id.easy_tab_id_image);
        tvlp.addRule(CENTER_HORIZONTAL);
        tvlp.bottomMargin = margin;
        mTextView.setLayoutParams(tvlp);
        mTextView.setSingleLine();
        mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        this.addView(mTextView);

        //设置水平间隔
        int hMargin = (int) (imgSize * 0.15);
        this.getBadgeConfigHelper().setBadgeHorizontalMarginDp(ResourceUtil.px2dip(getContext(), hMargin));
//        //添加Badger的基准线
//        View anchor = new View(mContext);
//        anchor.setId(R.id.easy_tab_id_badge_anchor);
//        LayoutParams anchorLp = new LayoutParams(10,10);
//        anchorLp.addRule(CENTER_HORIZONTAL);
//        anchor.setLayoutParams(anchorLp);
//        this.addView(anchor);
//
//        mBgaWrapper = new BadgeRelativeLayout(mContext);
//        mBgaWrapper.setId(R.id.easy_tab_id_badge_wrapper);
//        LayoutParams wrapperLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        wrapperLp.addRule(RIGHT_OF,R.id.easy_tab_id_badge_anchor);
//        wrapperLp.addRule(ALIGN_PARENT_RIGHT);
//        wrapperLp.leftMargin = (int) (imgSize * 0.15);
//        mBgaWrapper.setLayoutParams(wrapperLp);
//        mBgaWrapper.getBadgeConfigHelper().setBadgeGravity(Gravity. BadgeViewHelper.BadgeGravity.LeftTop);
//        mBgaWrapper.getBadgeViewHelper().setBadgeHorizontalMarginDp(0);
//        mBgaWrapper.getBadgeViewHelper().setBadgeVerticalMarginDp(2);
//        mBgaWrapper.addView(new View(mContext));
//        this.addView(mBgaWrapper);


    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * 可以重写此方法处理额外选中和取消选中的方法处理
     *
     * @param isSelect
     */
    @Override
    public void onTabSelected(boolean isSelect) {
        mImageView.setSelected(isSelect);
        mTextView.setSelected(isSelect);
        this.setSelected(isSelect);
    }
//
//    /**
//     * 显示圆圈
//     */
//    @Override
//    public void showCirclePointBadge() {
//        mBgaWrapper.showCirclePointBadge();
//    }
//
//    /**
//     * 显示未读数
//     *
//     * @param badgeText
//     */
//    @Override
//    public void showTextBadge(String badgeText) {
//        mBgaWrapper.showTextBadge(badgeText);
//    }
//
//    /**
//     * 隐藏标记
//     */
//    @Override
//    public void hiddenBadge() {
//        mBgaWrapper.hiddenBadge();
//    }
//
//    @Override
//    public boolean isBadgeShow() {
//        return mBgaWrapper.isBadgeShow();
//    }
//
//    @Override
//    public void setOnDragDismissListener(OnBadgeDragDismissListener onBadgeDragDismissListener) {
//        mBgaWrapper.setOnDragDismissListener(onBadgeDragDismissListener);
//    }
//
//    /**
//     * 指定标记背景
//     *
//     * @param bitmap
//     */
//    @Override
//    public void showDrawableBadge(Bitmap bitmap) {
//        mBgaWrapper.showDrawableBadge(bitmap);
//    }
//
//    @Override
//    public boolean callSuperOnTouchEvent(MotionEvent event) {
//        return mBgaWrapper.callSuperOnTouchEvent(event);
//    }
//
//    @Override
//    public void setPrimaryKey(Object o) {
//        mPrimaryKey = o;
//    }
//
//    @Override
//    public Object getPrimaryKey() {
//        return mPrimaryKey;
//    }
//
//
//    @Override
//    public BadgeConfig getBadgeConfigHelper() {
//        return mBgaWrapper.getBadgeConfigHelper();
//    }
}
