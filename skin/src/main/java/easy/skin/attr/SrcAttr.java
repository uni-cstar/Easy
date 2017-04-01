package easy.skin.attr;

import android.view.View;
import android.widget.ImageView;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 */

public class SrcAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable(SkinManager.getInstance().getResourceManager().getDrawable(attrValueRefName));
        }
    }
}
