package easy.app.page;

import android.content.Context;

import easy.R;

/**
 * Created by Lucio on 17/3/13.
 */

public class EasyLoadingView extends EasyPageView {

    public EasyLoadingView(Context context) {
        super(context, R.layout.view_easy_loading_page_view, false, MultiStateView.PageViewType.LOADING);
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
