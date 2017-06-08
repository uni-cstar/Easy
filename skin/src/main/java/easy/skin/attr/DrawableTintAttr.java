package easy.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.TextView;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/5/22.
 */

public class DrawableTintAttr extends SkinAttr {

    public static final String ATTR_NAME = "drawableTint";

    @Override
    public boolean apply(View view) {
        //如果控件不支持bg tint 则直接返回false
        if (!(view instanceof TextView))
            return false;

        ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
        if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        TextView tv = (TextView) view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setCompoundDrawableTintList(color);
        } else {
            Drawable[] drawables = tv.getCompoundDrawables();
            for (int i = 0; i < drawables.length; i++) {
                drawables[i] = tintDrawable(drawables[i], color);
            }
            tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
        return true;
    }

    private Drawable tintDrawable(Drawable drawable, ColorStateList color) {
        if (drawable == null)
            return null;

        Drawable d1 = drawable.mutate();
        d1.setBounds(drawable.copyBounds());
        DrawableCompat.setTintList(d1, color);
//        d1.setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_ATOP);
        return d1;
    }

    private static DrawableTintAttr newInstance() {
        return new DrawableTintAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }
}
