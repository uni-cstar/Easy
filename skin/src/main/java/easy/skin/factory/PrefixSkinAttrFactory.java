package easy.skin.factory;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.changeskin.utils.L;

import java.util.ArrayList;
import java.util.List;

import easy.skin.attr.SkinAttr;
import easy.skin.attr.SkinAttrSupport;

/**
 * Created by Lucio on 17/3/31.
 * 前缀属性工厂
 */

public class PrefixSkinAttrFactory extends SkinAttrFactory {

    private String mPrefix;

    private PrefixSkinAttrFactory(String prefix) {
        mPrefix = prefix;
    }

    public static PrefixSkinAttrFactory create(String prefix) {
        return new PrefixSkinAttrFactory(prefix);
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
