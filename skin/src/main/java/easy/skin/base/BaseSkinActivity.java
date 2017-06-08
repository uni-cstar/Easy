package easy.skin.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import easy.skin.SkinDelegate;
import easy.skin.SkinView;
import easy.skin.attr.SkinAttr;
import easy.skin.impl.ISkinDelegate;
import easy.skin.impl.SkinChangedListener;
import easy.skin.impl.SkinFontChangedListener;

/**
 * Created by Lucio on 17/3/31.
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinDelegate, SkinChangedListener, SkinFontChangedListener {

    SkinDelegate mSkinDelegateImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinDelegateImpl = SkinDelegate.create(this);
        mSkinDelegateImpl.setOnSkinChangedListener(this);
        mSkinDelegateImpl.setOnSkinFontChangedListener(this);
        mSkinDelegateImpl.beforeCallSuperOnCreate();
        super.onCreate(savedInstanceState);
        mSkinDelegateImpl.afterCallSuperOnCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSkinDelegateImpl.onDestroy();
    }

    @Override
    public void removeAllSkinView(View view) {
        mSkinDelegateImpl.removeAllSkinView(view);
    }

    @Override
    public void removeSkinView(View view) {
        mSkinDelegateImpl.removeSkinView(view);
    }

    @Override
    public void tryApplySkinView(View view) {
        mSkinDelegateImpl.tryApplySkinView(view);
    }

    @Override
    public SkinView addSkinView(View view, List<SkinAttr> skinAttrs) {
        return mSkinDelegateImpl.addSkinView(view, skinAttrs);
    }

    @Override
    public SkinView addSkinView(View view, String attrName, String resEntryName, String resTypeName) {
        return mSkinDelegateImpl.addSkinView(view, attrName, resEntryName, resTypeName);
    }

    @Override
    public void addFontChangeView(TextView textView) {
        mSkinDelegateImpl.addFontChangeView(textView);
    }

    @Override
    public void removeFontChangeView(TextView textView) {
        mSkinDelegateImpl.removeFontChangeView(textView);
    }

    /**
     * 应用换肤
     * 对外提供，用于主动触发换肤view属性的使用
     */
    protected void applySkinView() {
        mSkinDelegateImpl.onSkinChanged();
    }

    /**
     * 应用字体
     * 对外提供的方法，用于主动触发字体的切换
     */
    protected void applySkinFontView() {
        mSkinDelegateImpl.onSkinFontChanged();
    }

    /**
     * 皮肤切换回调方法
     */
    @Override
    public void onSkinChanged() {

    }

    /**
     * 字体切换回调方法
     */
    @Override
    public void onSkinFontChanged() {

    }
}
