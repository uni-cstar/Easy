package easy.skin;

import android.graphics.Typeface;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/4/19.
 */

public class FontRepository {

    private List<TextView> mTextViews;

    public FontRepository() {

    }

    public void add(TextView textView) {
        if (mTextViews == null) {
            mTextViews = new ArrayList<>();
        }
        mTextViews.add(textView);
        textView.setTypeface(TypefaceUtils.getCurrentTypeface());
    }

    public void clear() {
        mTextViews.clear();
    }

    public void remove(TextView textView) {
        mTextViews.remove(textView);
    }

    public void applyFont(Typeface tf) {

        if (SkinUtil.isNullOrEmpty(mTextViews))
            return;
        for (TextView textView : mTextViews) {
            textView.setTypeface(tf);
        }
    }
}
