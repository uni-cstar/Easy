package easy.skin.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 * src属性处理器
 */
class SrcAttr extends SkinAttr {
    @Override
    public boolean apply(View view) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            Drawable drawable = SkinManager.getInstance().getResourceManager().getDrawable(attrValueRefName);
            if(drawable == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
                return false;
            iv.setImageDrawable(drawable);
            return true;
        }
        return false;
    }
}
