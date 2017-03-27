package easy.app.page;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import easy.R;

/**
 * Created by Lucio on 17/3/13.
 */

public class EasyErrorView extends EasyPageView {

    /**
     * 是否已初始化
     */
    boolean initialed;
    String mErrorString;
    @DrawableRes
    int mErrorImgRes;

    ImageView errorImageView;
    TextView errorTextView;

    public EasyErrorView(Context context) {
        this(context, R.drawable.easy_ic_error, "加载出错了。");
    }

    public EasyErrorView(Context context, @DrawableRes int errorImg, String text) {
        super(context, R.layout.view_easy_error_page_view, false, MultiStateView.PageViewType.ERROR);
        this.mErrorString = text;
        mErrorImgRes = errorImg;
        initialed = false;
    }

    @Override
    public View getContentView() {
        View view = super.getContentView();
        if (!initialed) {
            initialed = true;
            errorImageView = (ImageView) view.findViewById(R.id.evErrorImage);
            errorTextView = (TextView) view.findViewById(R.id.evErrorText);
            errorImageView.setImageResource(mErrorImgRes);
            errorTextView.setText(mErrorString);
        }
        return view;
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
