package easy.skin.impl;

/**
 * Created by Lucio on 17/3/30.
 * 皮肤加载 Listener
 */

public interface SkinLoadListener {
    void onSkinLoadStart();

    void onSkinLoadFailed(Throwable e);

    void onSkinLoadSuccess();


}
