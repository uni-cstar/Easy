package easy.view.tab;

import android.view.View;

/**
 * Created by Lucio on 17/4/14.
 * EasyTabHost 包含的child的接口定义
 */
public interface IEasyTabItem {

    /**
     * 获取View
     *
     * @return
     */
    View getView();

    /**
     * 选中
     */
    void onTabSelected(boolean isSelect);


}
