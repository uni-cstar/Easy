package easy.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.view.View;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/5/3.
 * background tint属性处理器
 * api 21之前要使用此属性，最好使用基本控件对应的AppComat控件，如AppCompatTextView替换TextView，并将android:backgroundTint更改为app:backgroundTint
 * 也可以不是使用AppCompat控件，会尝试应用属性
 */
public class BackgroundTintAttr extends SkinAttr {

    public static final String ATTR_NAME = "backgroundTint";

    @Override
    public boolean apply(View view) {

        ColorStateList color = SkinManager.getInstance().getResourceManager().getColorStateList(resEntryName);
        if (color == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        if (view instanceof TintableBackgroundView || Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setBackgroundTintList(view, color);
        } else {
            Drawable drawable = view.getBackground();
            if (drawable == null)
                return false;
            Drawable d1 = drawable.mutate();
            //必须包装才能在低版本上进行渲染
            d1 = DrawableCompat.wrap(d1);
            DrawableCompat.setTintList(d1, color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(d1);
            } else {
                view.setBackgroundDrawable(d1);
            }
        }

        return true;

    }

    private static BackgroundTintAttr newInstance() {
        return new BackgroundTintAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }
}
