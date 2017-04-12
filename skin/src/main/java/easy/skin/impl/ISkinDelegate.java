package easy.skin.impl;

import android.view.View;

import java.util.List;

import easy.skin.attr.SkinAttr;

/**
 * Created by Lucio on 17/4/10.
 */

public interface ISkinDelegate {

    void removeAllSkinView(View view);

    void removeSkinView(View view);

    /**
     * 添加换肤View
     * @param view
     * @param skinAttrs 换肤属性
     */
    void addSkinView(View view, List<SkinAttr> skinAttrs);

    /**
     * 添加换肤View
     * @param view
     * @param attrName     属性名字 eg:background
     * @param resEntryName 资源名字 eg:login_bg
     * @param resTypeName  资源类型 eg:drawable color
     */
    void addSkinView(View view, String attrName, String resEntryName, String resTypeName);
}
