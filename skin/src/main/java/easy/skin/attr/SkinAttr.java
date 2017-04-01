package easy.skin.attr;

import android.view.View;

/**
 * Created by Lucio on 17/3/31.
 */
public abstract class SkinAttr implements Cloneable {

    public static final String RES_TYPE_NAME_COLOR = "color";
    public static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    public static final String RES_TYPE_NAME_MIPMAP = "mipmap";

    /**
     * 属性名字 eg: background ,textColor
     */
    public String attrName;

    /**
     * 资源名字, eg:login_bg
     */
    public String attrValueRefName;

    /**
     * 属性值类型。eg: color or drawable
     */
    public String attrValueTypeName;

    protected boolean isDrawable() {
        return RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)
                || RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName);
    }

    protected boolean isColor() {
        return RES_TYPE_NAME_COLOR.equals(attrValueTypeName);
    }

    /**
     * 应用属性
     *
     * @param view
     */
    public abstract void apply(View view);

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
