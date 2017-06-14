package easy.skin.attr;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v4.widget.TintableCompoundButton;
import android.view.View;
import android.widget.CompoundButton;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/6/14.
 */

public class CompoundButtonTintAttr extends SkinAttr {

    public static final String ATTR_NAME = "buttonTint";

    @Override
    public boolean apply(View view) {
        ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
        if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        if (view instanceof TintableCompoundButton) {
            ((TintableCompoundButton) view).setSupportButtonTintList(color);
        } else if (view instanceof CompoundButton) {
            CompoundButton button = (CompoundButton) view;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                button.setButtonTintList(color);
            } else {
                CompoundButtonCompat.setButtonTintList(button, color);
            }
        } else {
            return false;
        }

        return true;
    }

    private static CompoundButtonTintAttr newInstance() {
        return new CompoundButtonTintAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }


}