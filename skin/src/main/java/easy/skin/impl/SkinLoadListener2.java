package easy.skin.impl;

/**
 * Created by Lucio on 17/3/30.
 * 从网络加载皮肤Listener
 */

public interface SkinLoadListener2 extends SkinLoadListener {
    /**
     * called when from network load skin
     *
     * @param progress download progress
     */
    void onProgress(int progress);

}
