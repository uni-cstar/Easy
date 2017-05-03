package easy.ucnt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import easy.utils.ListUtil;

/**
 * Created by Lucio on 17/4/20.
 * 未读数管理类
 */

public class UnreadManager {

    private HashMap<String, Unread> mUnreadList;
    private List<OnUnreadChangedListener> mListeners = new ArrayList<>();
    UnreadDBHelper mDbHelper;

    private UnreadManager() {
    }

    private static class Holder {
        private static UnreadManager instance = new UnreadManager();
    }

    public static UnreadManager getInstance() {
        return Holder.instance;
    }

    /**
     * 初始化未读数
     *
     * @param loader
     */
    public void setup(UnreadDBHelper loader) {
        mDbHelper = loader;
    }

    public void initUnread() {
        mUnreadList = mDbHelper.loadUnreadList();
        notifyUnreadInitListener();
    }

    /**
     * 添加未读数监听
     *
     * @param listener
     */
    public void addOnUnreadChangedListener(OnUnreadChangedListener listener) {
        //预防重复添加
        if (mListeners.contains(listener))
            return;
        mListeners.add(listener);
    }

    /**
     * 移除未读数监听
     *
     * @param listener
     */
    public void removeOnUnreadChangedListener(OnUnreadChangedListener listener) {
        mListeners.remove(listener);
    }

    /**
     * 通知初始化成功
     */
    public void notifyUnreadInitListener() {
        for (OnUnreadChangedListener listener : mListeners) {
            listener.onUnreadInit();
        }
    }


    public void clearUnread(String key) {
        if (mUnreadList == null || mUnreadList.size() == 0)
            return;

        if (mUnreadList.containsKey(key)) {

        } else {
            Collection<Unread> values = mUnreadList.values();
            for (Unread item : values) {
                if (item.contains(key)) {

                }
            }
        }
    }

    /**
     * 尝试清除未读数
     *
     * @param key
     * @return
     */
    public boolean tryClearCount(String key) {
        if (mUnreadList == null || mUnreadList.size() == 0)
            return;
        if (this.key.equals(key)) {
            this.count = 0;
            for (Unread item : children) {

            }
        }

        if (ListUtil.isNullOrEmpty(children))
            return false;

        for (Unread item : children) {
            if (item.key.equals(key)) {
                //重新计算总的未读数
                this.count = Math.max(0, this.count - item.count);
                //清除child未读数
                item.count = 0;
                return true;
            }
        }
        return false;
    }


    public void setUnread(String key, int count, @Unread.Type int type) {
        if (ListUtil.isNullOrEmpty(mUnreadList))
            return;

        for (Unread item : mUnreadList) {

        }
    }
}
