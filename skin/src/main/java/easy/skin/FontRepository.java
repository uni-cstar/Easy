package easy.skin;

import android.graphics.Typeface;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/4/19.
 */

public class FontRepository {

    private List<SoftReference<TextView>> mTextViews;

    public FontRepository() {

    }

    public void add(TextView textView) {
        if (mTextViews == null) {
            mTextViews = new ArrayList<>();
        }

        mTextViews.add(new SoftReference<TextView>(textView));
        textView.setTypeface(TypefaceUtils.getCurrentTypeface());
    }

    public void clear() {
        mTextViews.clear();
    }

    public void remove(TextView textView) {
        Iterator<SoftReference<TextView>> iterator = mTextViews.iterator();
        while (iterator.hasNext()){
            SoftReference<TextView> viewRef = iterator.next();
            TextView view = viewRef.get();
            if(view == null || view == textView){
                iterator.remove();
            }
        }

    }

    public void applyFont(Typeface tf) {

        if (SkinUtil.isNullOrEmpty(mTextViews))
            return;

        Iterator<SoftReference<TextView>> iterator = mTextViews.iterator();
        while (iterator.hasNext()){
            SoftReference<TextView> viewRef = iterator.next();
            TextView view = viewRef.get();
            if(view == null){
                iterator.remove();
            }else{
                view.setTypeface(tf);
            }
        }
//        for (TextView textView : mTextViews) {
//            textView.setTypeface(tf);
//        }
    }
}
