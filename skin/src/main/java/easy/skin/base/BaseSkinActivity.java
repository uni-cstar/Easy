package easy.skin.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import easy.skin.SkinDelegate;
import easy.skin.attr.SkinAttr;
import easy.skin.impl.ISkinActivityDelegate;
import easy.skin.impl.ISkinDelegate;

/**
 * Created by Lucio on 17/3/31.
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinDelegate {

    ISkinActivityDelegate mSkinDelegateImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinDelegateImpl = SkinDelegate.create(this);
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
    public void addSkinView(View view, List<SkinAttr> skinAttrs) {
        mSkinDelegateImpl.addSkinView(view, skinAttrs);
    }
}
