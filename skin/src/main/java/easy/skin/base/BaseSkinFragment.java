package easy.skin.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import easy.skin.attr.SkinAttr;
import easy.skin.impl.ISkinDelegate;

/**
 * Created by Lucio on 17/4/10.
 */
public class BaseSkinFragment extends Fragment implements ISkinDelegate {

    ISkinDelegate mSkinDelegateImpl;

    /**
     * 绑定
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISkinDelegate) {
            mSkinDelegateImpl = (ISkinDelegate) context;
        } else {
            onDetach();
        }
    }

    /**
     * 解绑
     */
    @Override
    public void onDetach() {
        super.onDetach();
        onDestroyView();
        mSkinDelegateImpl = null;
    }

    /**
     * 当前Fragment是否缓存了View
     * 可以重写此方法改变onDestroyView移除view
     * @return
     */
    public boolean isCacheView(){
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(isCacheView())
            return;
        View view = getView();
        if (view == null)
            return;
        if (mSkinDelegateImpl != null)
            mSkinDelegateImpl.removeAllSkinView(view);
    }


    @Override
    public void removeAllSkinView(View view) {
        if (mSkinDelegateImpl != null)
            mSkinDelegateImpl.removeAllSkinView(view);
    }

    @Override
    public void removeSkinView(View view) {
        if (mSkinDelegateImpl != null)
            mSkinDelegateImpl.removeSkinView(view);
    }

    @Override
    public void addSkinView(View view, List<SkinAttr> skinAttrs) {
        if (mSkinDelegateImpl != null)
            mSkinDelegateImpl.addSkinView(view, skinAttrs);
    }

}