package easy.skin.attr;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 * background属性处理器
 */
class BackgroundAttr extends SkinAttr{

    @Override
    public boolean apply(View view) {
        if (isColor()) {
            int color = SkinManager.getInstance().getResourceManager().getColor(attrValueRefName);
            if(color == 0 && SkinAttrSupport.isIgnoreWhenAttrNotFound())
                return false;
            if (view instanceof CardView) {//CardView特殊处理下
                CardView cardView = (CardView) view;
                //给CardView设置背景色应该使用cardBackgroundColor，直接使用background没有圆角效果
                cardView.setCardBackgroundColor(color);
            } else {
                view.setBackgroundColor(color);
            }
            return true;
        } else if (isDrawable()) {
            Drawable bg = SkinManager.getInstance().getResourceManager().getDrawable(attrValueRefName);
            if(bg == null && SkinAttrSupport.isIgnoreWhenAttrNotFound())
                return false;
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(bg);
            } else {
                view.setBackgroundDrawable(bg);
            }
            return true;
        }
        return false;
    }
}
