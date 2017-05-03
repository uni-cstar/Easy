package easy.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.View;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/5/3.
 * background tint属性处理器
 */
public class BackgroundTintAttr extends SkinAttr {

    private static final String ATTR_NAME = "backgroundTint";

    @Override
    public boolean apply(View view) {
        //如果控件不支持bg tint 则直接返回false
        if (!(view instanceof TintableBackgroundView))
            return false;

        ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
        if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        ViewCompat.setBackgroundTintList(view, color);

        return true;
    }

    private static BackgroundTintAttr newInstance() {
        return new BackgroundTintAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME,newInstance());
    }
}
