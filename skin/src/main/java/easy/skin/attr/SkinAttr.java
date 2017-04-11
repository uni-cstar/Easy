package easy.skin.attr;

import android.view.View;

import easy.skin.SkinConst;

/**
 * Created by Lucio on 17/3/31.
 */
public abstract class SkinAttr implements Cloneable {

    /**
     * 属性名字 eg: background ,textColor
     */
    public String attrName;

    /**
     * 资源名字, eg:login_bg
     */
    public String resEntryName;

    /**
     * 属性值类型。eg: color or drawable
     */
    public String resTypeName;

    protected boolean isDrawable() {
        return SkinConst.RES_TYPE_NAME_DRAWABLE.equals(resTypeName)
                || SkinConst.RES_TYPE_NAME_MIPMAP.equals(resTypeName);
    }

    protected boolean isColor() {
        return SkinConst.RES_TYPE_NAME_COLOR.equals(resTypeName);
    }

    /**
     * 应用属性
     *
     * @param view
     * @return true:更改了属性
     * false:没有更改属性
     */
    public abstract boolean apply(View view);

    @Override
    public SkinAttr clone() {
        SkinAttr o = null;
        try {
            o = (SkinAttr) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
