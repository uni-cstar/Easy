package easy.skin.attr;

import android.view.View;
import android.widget.TextView;

import easy.skin.SkinConst;
import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/4/11.
 */

public class TextAttr extends SkinAttr {
    @Override
    public boolean apply(View view) {
        if (view instanceof TextView) {
            if (!SkinConst.ATTR_NAME_TEXT.equals(attrName) ||
                    !SkinConst.RES_TYPE_NAME_STRING.equals(resTypeName))
                return false;
            CharSequence text = SkinManager.getInstance().getResourceManager().getText(resEntryName);
            if (text == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
                return false;
            ((TextView) view).setText(text);
            return true;
        }
        return false;
    }
}
