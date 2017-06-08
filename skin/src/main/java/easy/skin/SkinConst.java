package easy.skin;

import easy.skin.attr.BackgroundTintAttr;
import easy.skin.attr.ImageViewSrcTintAttr;

/**
 * Created by Lucio on 17/4/7.
 * 常量
 */

public class SkinConst {

    /**
     * 资源类别名字
     */
    public static final String RES_TYPE_NAME_COLOR = "color";
    public static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    public static final String RES_TYPE_NAME_MIPMAP = "mipmap";
    public static final String RES_TYPE_NAME_STRING = "string";
    public static final String RES_TYPE_NAME_DIMEN = "dimen";
    public static final String RES_TYPE_NAME_INTEGER = "integer";
    /**
     * 默认支持的属性
     */
    public static final String ATTR_NAME_BACKGROUND = "background";
    public static final String ATTR_NAME_TEXTCOLOR = "textColor";
    public static final String ATTR_NAME_SRC = "src";
    public static final String ATTR_NAME_TEXT = "text";

    public static final String ATTR_NAME_BACKGROUND_TINT = BackgroundTintAttr.ATTR_NAME;
    public static final String ATTR_NAME_IMAGE_VIEW_SRC_TINT = ImageViewSrcTintAttr.ATTR_NAME;

    /**
     * 命名空间 在布局文件中申明
     */
    public static final String NAMESPACE = "http://schemas.android.com/android/skin";

    /**
     * 属性标记 eg: skin:enable="true"
     */
    public static final String NAMESPACE_ATTR_SKIN_ENABLE = "enable";

    /**
     * 默认前缀
     */
    public static final String DEFAULT_PREFIX = "skin";

    public static final String FONT_DIR = "fonts";
}
