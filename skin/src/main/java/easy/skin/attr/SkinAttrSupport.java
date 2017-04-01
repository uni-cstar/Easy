package easy.skin.attr;

import java.util.HashMap;

/**
 * Created by Lucio on 17/3/31.
 * 支持的属性
 */
public class SkinAttrSupport {

    /**
     * 支持的属性
     */
    private static HashMap<String, SkinAttr> sSupportAttr = new HashMap<>();

    static {
        sSupportAttr.put("background", new BackgroundAttr());
        sSupportAttr.put("textColor", new TextColorAttr());
        sSupportAttr.put("src", new SrcAttr());
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
        SkinAttr mSkinAttr = sSupportAttr.get(attrName).clone();
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
        return sSupportAttr.containsKey(attrName);
    }

    /**
     * 添加支持的属性
     *
     * @param attrName 属性名字
     * @param skinAttr 属性实现类
     */
    public static void addSupportAttr(String attrName, SkinAttr skinAttr) {
        sSupportAttr.put(attrName, skinAttr);
    }

    /**
     * 移除支持的属性
     * @param attrName 属性名字
     */
    public static void removeSupport(String attrName){
        sSupportAttr.remove(attrName);
    }
}
