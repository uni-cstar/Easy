package easy.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

import easy.skin.SkinManager;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/5/3.
 * ImageView src tint支持
 * 如果要在5.0之前使用此属性，需要不xml中android:tint更换为app:tint
 */

public class ImageViewSrcTintAttr extends SkinAttr {

    public static final String ATTR_NAME = "tint";

    @Override
    public boolean apply(View view) {
        //不是ImageView 不处理
        if (!(view instanceof ImageView))
            return false;

        ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
        if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;
        return setTintList((ImageView) view, color);
    }

    /**
     * 可以重写此方法设置不同的mode
     *
     * @param view
     * @param color
     * @return
     */
    public boolean setTintList(ImageView view, ColorStateList color) {
        return SkinUtil.setTintList(view, color);
    }


    private static ImageViewSrcTintAttr newInstance() {
        return new ImageViewSrcTintAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }
}
