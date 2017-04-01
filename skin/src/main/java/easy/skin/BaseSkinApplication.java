package easy.skin;

import android.app.Application;

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
