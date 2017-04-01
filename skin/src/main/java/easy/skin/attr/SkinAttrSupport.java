package easy.skin.attr;

import java.util.HashMap;

/**
 * Created by Lucio on 17/3/31.
 * 支持的属性
 */
public class SkinAttrSupport {

    public static final String DEF_SUPPORT_ATTR_BACKGROUND="background";
    public static final String DEF_SUPPORT_ATTR_TEXTCOLOR="textColor";
    public static final String DEF_SUPPORT_ATTR_SRC="src";
    /**
     * 支持的属性
     */
    private static HashMap<String, SkinAttr> mSupportAttrs = new HashMap<>();

    private static boolean ignoreWhenAttrNotFound = true;

    static {
        mSupportAttrs.put(DEF_SUPPORT_ATTR_BACKGROUND, new BackgroundAttr());
        mSupportAttrs.put(DEF_SUPPORT_ATTR_TEXTCOLOR, new TextColorAttr());
        mSupportAttrs.put(DEF_SUPPORT_ATTR_SRC, new SrcAttr());
    }

    public static boolean isIgnoreWhenAttrNotFound(){
        return ignoreWhenAttrNotFound;
    }

    /**
     * 设置资源未找到时是否忽略设置控件对应值。
     * PS:比如一个TextView是需要进行皮肤切换的，
     * 但是切换的时候找不到对应的资源，哪么这个时候得到的资源ID为0，
     * 如果还是将结果设置给TextView的话，会导致取消TextView的一些默认效果，
     * 设置为True将会忽略资源不存在的结果。
     * @param ignore
     */
    public static void setIgnoreWhenAttrNotFound(boolean ignore){
        ignoreWhenAttrNotFound = ignore;
    }

    /**
     * 生成属性
     *
     * @param attrName         属性名字 eg:background
     * @param attrValueRefName 资源名字 eg:login_bg
     * @param typeName         资源类型 eg:drawable color
     * @return
     */
    public static SkinAttr genSkinAttr(String attrName, String attrValueRefName, String typeName) {
        SkinAttr mSkinAttr = mSupportAttrs.get(attrName).clone();
        if (mSkinAttr == null) return null;
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }


    /**
     * 是否是支持的属性
     *
     * @param attrName 属性名字。eg: background/textColor
     * @return true : 支持
     * false: false 不支持
     */
    public static boolean isSupportedAttr(String attrName) {
        return mSupportAttrs.containsKey(attrName);
    }

    /**
     * 添加支持的属性
     *
     * @param attrName 属性名字
     * @param skinAttr 属性实现类
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        mSupportAttrs.put(attrName, skinAttr);
    }

    /**
     * 移除支持的属性
     * @param attrName 属性名字
     */
    public static void removeSupport(String attrName){
        mSupportAttrs.remove(attrName);
    }
}
