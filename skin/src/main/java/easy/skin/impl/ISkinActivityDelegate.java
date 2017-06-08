package easy.skin.impl;

/**
 * Created by Lucio on 17/4/11.
 */
public interface ISkinActivityDelegate extends ISkinDelegate {

    void beforeCallSuperOnCreate();

    void afterCallSuperOnCreate();

    void onDestroy();

    void setEnableSkinSwitchAnim(boolean enableSkinSwitchAnim);

    void setIsSkinSwitchAnimAlways(boolean isSkinSwitchAnimAlways);

    void setOnSkinChangedListener(SkinChangedListener skinChangeListener);

    /**
     * 设置字体切换监听
     *
     * @param fontChangedListener
     */
    void setOnSkinFontChangedListener(SkinFontChangedListener fontChangedListener);

}
