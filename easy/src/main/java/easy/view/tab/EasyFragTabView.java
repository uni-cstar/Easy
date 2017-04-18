package easy.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lucio on 17/4/18.
 */

public class EasyFragTabView extends EasyTabView implements IFragTabItem{

    private String mTag;

    public EasyFragTabView(Context context,String tag) {
        super(context);
        mTag = tag;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public String getFragTag() {
        return mTag;
    }
}
