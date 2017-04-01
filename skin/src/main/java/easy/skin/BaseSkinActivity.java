package easy.skin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lucio on 17/3/31.
 */

public class BaseSkinActivity extends AppCompatActivity {

    SkinDelegate mSkinDelegate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinDelegate = new SkinDelegate(this);
        mSkinDelegate.beforeCallSuperOnCreate();
        super.onCreate(savedInstanceState);
        mSkinDelegate.afterCallSuperOnCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSkinDelegate.onDestroy();
    }
}
