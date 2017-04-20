package easy.skin.factory;

import easy.skin.SkinConst;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/31.
 * 前缀属性工厂：属性值名字必须以指定前缀命名开头
 */

class PrefixSkinAttrFactory extends SkinAttrFactory {

    private String mPrefix;

    PrefixSkinAttrFactory(String prefix) {
        super();
        mPrefix = SkinUtil.isNullOrEmpty(prefix) ? SkinConst.DEFAULT_PREFIX : prefix;
    }

    PrefixSkinAttrFactory() {
        this(SkinConst.DEFAULT_PREFIX);
    }

    @Override
    protected boolean addRefAttr(String attrName, int attrValueRefId, String entryName, String typeName) {
        return entryName.startsWith(mPrefix);
    }


}
