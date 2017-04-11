package easy.skin.attr;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 * textColor属性处理器
 */
class TextColorAttr extends SkinAttr {

    @Override
    public boolean apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (isColor()) {
                ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
                if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
                    return false;
                tv.setTextColor(color);
                return true;
            }
        }
        return false;
    }

}
