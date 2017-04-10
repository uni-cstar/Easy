package easy.skin.factory;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import easy.skin.SkinConst;
import easy.skin.attr.SkinAttr;

/**
 * Created by Lucio on 17/4/10.
 * 混合属性工厂：必须命名空间申明并且标记属性enable＝true，且关联属性值为指定前缀命名开头
 */

public class BlendSkinAttrFactory extends PrefixSkinAttrFactory {

    @Override
    public List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        //是否配备了
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConst.NAMESPACE, SkinConst.NAMESPACE_ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable)
            return null;
        return super.getSkinAttrs(attrs, context);
    }

}
