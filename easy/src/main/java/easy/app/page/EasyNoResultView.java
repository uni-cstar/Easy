

package easy.app.page;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import easy.R;

/**
 * Created by Lucio on 17/3/13.
 * 简易的无结果 view
 */

public class EasyNoResultView extends EasyPageView {

    /**
     * 是否已初始化
     */
    boolean initialed;

    String mNoResultString;
    @DrawableRes
    int mNoResultImgRes;

    ImageView noResultImageView;
    TextView noResultTextView;

    public EasyNoResultView(Context context) {
        this(context, R.drawable.easy_ic_no_result, null);
    }

    public EasyNoResultView(Context context, @DrawableRes int noResultImg, String text) {
        super(context, R.layout.view_easy_no_result_page_view, false, MultiStateView.PageViewType.NO_RESULT);
        this.mNoResultString = text;
        mNoResultImgRes = noResultImg;
        initialed = false;
    }

    @Override
    public View getContentView() {
        View view = super.getContentView();
        if (!initialed) {
            initialed = true;
            noResultImageView = (ImageView) view.findViewById(R.id.evErrorImage);
            noResultTextView = (TextView) view.findViewById(R.id.evErrorText);
            noResultImageView.setImageResource(mNoResultImgRes);
            if (mNoResultString == null || mNoResultString.length() == 0) {
                noResultTextView.setVisibility(View.GONE);
            } else {
                noResultTextView.setVisibility(View.VISIBLE);
                noResultTextView.setText(mNoResultString);
            }
        }
        return view;
    }

    public void setContentText(String content) {
        mNoResultString = content;
        if (mNoResultString == null || mNoResultString.length() == 0) {
            noResultTextView.setVisibility(View.GONE);
        } else {
            noResultTextView.setVisibility(View.VISIBLE);
            noResultTextView.setText(mNoResultString);
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
