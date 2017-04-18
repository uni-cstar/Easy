package easy.view.tab;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
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
public class EasyTabView extends BadgeRelativeLayout{

    Context mContext;
    ImageView mImageView;
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
//        tvlp.bottomMargin = margin;
        mTextView.setLayoutParams(tvlp);
        mTextView.setSingleLine();
        mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        this.addView(mTextView);

        //设置水平间隔
        int hMargin = (int) (imgSize * 0.15);
        this.getBadgeConfigHelper().setBadgeHorizontalMarginDp(ResourceUtil.px2dip(getContext(), hMargin));
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }


    /**
     * 可以重写此方法处理额外选中和取消选中的方法处理
     *
     * @param isSelect
     */
    public void onTabSelected(boolean isSelect) {
        mImageView.setSelected(isSelect);
        mTextView.setSelected(isSelect);
        this.setSelected(isSelect);
    }
}
