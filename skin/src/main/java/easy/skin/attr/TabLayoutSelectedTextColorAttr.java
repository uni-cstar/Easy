package easy.skin.attr;

import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.view.View;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/6/14.
 */

public class TabLayoutSelectedTextColorAttr extends SkinAttr {

    public static final String ATTR_NAME = "tabSelectedTextColor";

    @Override
    public boolean apply(View view) {
        int color = SkinManager.getInstance().getResourceManager().getColor(resEntryName);
        if (color == 0 && SkinAttrSupport.isIgnoreWhenAttrNotFound())
            return false;

        if (view instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) view;
            tabLayout.setTabTextColors(createColorStateList(tabLayout.getTabTextColors().getDefaultColor(), color));
        }
        return true;
    }

    private ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;

        states[i] = new int[]{android.R.attr.state_selected};
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = new int[]{};
        colors[i] = defaultColor;
        i++;

        return new ColorStateList(states, colors);
    }

    private static TabLayoutSelectedTextColorAttr newInstance() {
        return new TabLayoutSelectedTextColorAttr();
    }

    /**
     * 添加到支持的属性中
     */
    public static void addToSupportAttr() {
        SkinAttrSupport.addSupportAttr(ATTR_NAME, newInstance());
    }


}