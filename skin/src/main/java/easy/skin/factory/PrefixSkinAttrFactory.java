package easy.skin.factory;

import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/31.
 * 前缀属性工厂
 */

class PrefixSkinAttrFactory extends SkinAttrFactory {

    /**
     * 默认前缀
     */
    static final String DEFAULT_PREFIX = "skin";

    private String mPrefix;

    PrefixSkinAttrFactory(String prefix) {
        mPrefix = SkinUtil.isNullOrEmpty(prefix) ? DEFAULT_PREFIX : prefix;
    }

    PrefixSkinAttrFactory() {
        this(DEFAULT_PREFIX);
    }

//
//    @Override
//    public List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
//        List<SkinAttr> skinAttrs = new ArrayList<>();
//        for (int i = 0; i < attrs.getAttributeCount(); i++) {//遍历属性
//            String attrName = attrs.getAttributeName(i);//属性名字，eg:background
//            String attrValue = attrs.getAttributeValue(i);
//
//            //如果是支持的属性，并且属性值是引用。 eg: android:background="@drawable/launcher"
//            if (SkinAttrSupport.isSupportedAttr(attrName) && attrValue.startsWith("@")) {
//                int id = Integer.parseInt(attrValue.substring(1));
//                String entryName = context.getResources().getResourceEntryName(id);//入口名，eg:launcher
//                String typeName = context.getResources().getResourceTypeName(id);//类型名，eg:drawable
//
//                L.e("entryName = " + entryName);
//                if (entryName.startsWith(mPrefix)) {
//                    SkinAttr skinAttr = SkinAttrSupport.genSkinAttr(attrName, id, entryName, typeName);
//                    skinAttrs.add(skinAttr);
//                }
//            }
//        }
//        return skinAttrs;
//    }

    @Override
    protected boolean addRefAttr(String attrName, int attrValueRefId, String entryName, String typeName) {
        return entryName.startsWith(mPrefix);
    }


}
