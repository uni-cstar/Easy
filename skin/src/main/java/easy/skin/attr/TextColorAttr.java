package easy.skin.attr;

import android.view.View;
import android.widget.TextView;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 */

public class TextColorAttr extends SkinAttr{
    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (isColor()) {
                tv.setTextColor(SkinManager.getInstance().getResourceManager().getColorStateList(attrValueRefName));
            }
        }
    }
}
