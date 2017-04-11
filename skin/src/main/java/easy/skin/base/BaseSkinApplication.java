package easy.skin.base;

import android.app.Application;

import easy.skin.SkinManager;

/**
 * Created by Lucio on 17/3/31.
 */
public class BaseSkinApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
