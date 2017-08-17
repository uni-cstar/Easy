package easy.skin.attr;

import android.support.design.widget.TabLayout;
import android.view.View;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/6/14.
 */

public class TabLayoutIndicatorColorAttr extends SkinAttr {

    public static final String ATTR_NAME = "tabIndicatorColor";

    @Override
    public boolean apply(View view) {
        int color = SkinManager.getInstance().getResourceManager().getColor(resEntryName);
        if (color == 0 && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        if (view instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) view;
            tabLayout.setSelectedTabIndicatorColor(color);
        }

        return true;
    }

    private static TabLayoutIndicatorColorAttr newInstance() {
        return new TabLayoutIndicatorColorAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }


}