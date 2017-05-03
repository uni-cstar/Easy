package easy.app.page;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import easy.R;

/**
 * Created by Lucio on 17/4/28.
 */
public class EasyEmptyView extends EasyPageView {

    /**
     * 是否已初始化
     */
    boolean initialed;

    String mEmptyString;

    @DrawableRes
    int mEmptyImgRes;

    ImageView emptyImageView;
    TextView emptyTextView;

    public EasyEmptyView(Context context) {
        this(context, R.drawable.easy_ic_empty, null);
    }

    public EasyEmptyView(Context context, @DrawableRes int emptyImg, String text) {
        super(context, R.layout.view_easy_no_result_page_view, false, MultiStateView.PageViewType.EMPTY);
        this.mEmptyString = text;
        mEmptyImgRes = emptyImg;
        initialed = false;
    }

    @Override
    public View getContentView() {
        View view = super.getContentView();
        if (!initialed) {
            initialed = true;
            emptyImageView = (ImageView) view.findViewById(R.id.evErrorImage);
            emptyTextView = (TextView) view.findViewById(R.id.evErrorText);
            emptyImageView.setImageResource(mEmptyImgRes);
            setContentText(mEmptyString);
        }
        return view;
    }

    public void setContentText(String content){
        mEmptyString = content;
        if (mEmptyString == null || mEmptyString.length() == 0) {
            emptyTextView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(mEmptyString);
        }
    }

    @Override
    public void hidePageView() {
        super.hidePageView();
    }

    @Override
    public void showPageView() {
        super.showPageView();
    }

}
