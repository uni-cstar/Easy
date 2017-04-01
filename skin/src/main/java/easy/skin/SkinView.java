package easy.skin;

import android.view.View;

import java.util.List;

import easy.skin.attr.SkinAttr;
import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/31.
 */

public class SkinView {

    View view;
    List<SkinAttr> attrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.attrs = skinAttrs;
    }

    public void apply() {
        if (view == null || SkinUtil.isNullOrEmpty(attrs)) return;
        for (SkinAttr attr : attrs) {
            attr.apply(view);
        }
    }
}



