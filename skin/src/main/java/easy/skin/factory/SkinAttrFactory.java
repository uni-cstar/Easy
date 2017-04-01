package easy.skin.factory;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import easy.skin.attr.SkinAttr;
import easy.skin.attr.SkinAttrSupport;

/**
 * Created by Lucio on 17/3/31.
 * 属性创造工厂
 */

public abstract class SkinAttrFactory {

    public List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {//遍历属性
            String attrName = attrs.getAttributeName(i);//属性名字，eg:background
            String attrValue = attrs.getAttributeValue(i);

            //处理样式
            if ("style".equals(attrName)) {//style 属性 eg:style="@style/textStyle"
                String styleName = attrValue.substring(attrValue.indexOf("/") + 1);//eg: textStyle
                int styleID = context.getResources().getIdentifier(styleName, "style", context.getPackageName());
                int[] styleAttrs = new int[]{
                        android.R.attr.textColor,
                        android.R.attr.background,
                        android.R.attr.src};
                TypedArray a = context.getTheme().obtainStyledAttributes(styleID, styleAttrs);

                //处理样式中的textColor
//                int textColor = a.getColor(0, -1);
                int textColorId = a.getResourceId(0, -1);
                if (textColorId != -1) {//&&textColor != -1
                    String entryName = context.getResources().getResourceEntryName(textColorId);
                    String typeName = context.getResources().getResourceTypeName(textColorId);
                    if (addRefAttr("textColor", textColorId, entryName, typeName)) {
                        SkinAttr skinAttr = SkinAttrSupport.genSkinAttr(attrName, entryName, typeName);
                        if (skinAttr != null) {
                            skinAttrs.add(skinAttr);
                        }
                    }
                }

                //处理background
//                int background = a.getColor(1, -1);
                int backgroundId = a.getResourceId(1, -1);
                if (backgroundId != -1) {//&&background != -1
                    String entryName = context.getResources().getResourceEntryName(backgroundId);
                    String typeName = context.getResources().getResourceTypeName(backgroundId);

                    if (addRefAttr("background", backgroundId, entryName, typeName)) {
                        SkinAttr skinAttr = SkinAttrSupport.genSkinAttr(attrName, entryName, typeName);
                        if (skinAttr != null) {
                            skinAttrs.add(skinAttr);
                        }
                    }
                }

                //处理src id
                int srcId = a.getResourceId(2, -1);
                if (srcId != -1) {
                    String entryName = context.getResources().getResourceEntryName(srcId);
                    String typeName = context.getResources().getResourceTypeName(srcId);

                    if (addRefAttr("src", backgroundId, entryName, typeName)) {
                        SkinAttr skinAttr = SkinAttrSupport.genSkinAttr(attrName, entryName, typeName);
                        if (skinAttr != null) {
                            skinAttrs.add(skinAttr);
                        }
                    }
                }
                a.recycle();
                continue;
            }
            //如果是支持的属性，并且属性值是引用。 eg: android:background="@drawable/launcher"
            if (SkinAttrSupport.isSupportedAttr(attrName) && attrValue.startsWith("@")) {
                int id = Integer.parseInt(attrValue.substring(1));
                String entryName = context.getResources().getResourceEntryName(id);//入口名，eg:launcher
                String typeName = context.getResources().getResourceTypeName(id);//类型名，eg:drawable
                //是否添加引用属性
                if (addRefAttr(attrName, id, entryName, typeName)) {
                    skinAttrs.add(SkinAttrSupport.genSkinAttr(attrName, entryName, typeName));
                }
            }
        }
        return skinAttrs;
    }

    /**
     * 是否添加属性
     *
     * @param attrName
     * @param attrValueRefId
     * @param entryName
     * @param typeName
     * @return
     */
    protected abstract boolean addRefAttr(String attrName, int attrValueRefId, String entryName, String typeName);

}
