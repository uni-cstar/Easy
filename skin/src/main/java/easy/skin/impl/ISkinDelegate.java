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
     * @param view
     * @param skinAttrs 换肤属性
     */
    void addSkinView(View view, List<SkinAttr> skinAttrs);
}
