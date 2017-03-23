package easy.app.page;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by Lucio on 17/3/13.
 */

public class LoadingPageView extends EasyPageView {

    public LoadingPageView(Context context, @LayoutRes int layoutId, boolean autoShowWhenCreate, @MultiStateView.PageViewType int type) {
        super(context, layoutId, autoShowWhenCreate, type);
    }

    public LoadingPageView(View view, boolean autoShowWhenCreate, @MultiStateView.PageViewType int type) {
        super(view, autoShowWhenCreate, type);
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
