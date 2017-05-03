package easy.ucnt;

import java.util.List;

import easy.utils.ListUtil;

/**
 * Created by Lucio on 17/4/21.
 */

public class UnreadParent extends Unread {

    List<UnreadChild> mChildren;

    public UnreadParent(UnreadEvent event) {
        super(event);
    }

    @Override
    public UnreadParent getParent() {
        return null;
    }

    @Override
    public List<UnreadChild> getChildren() {
        return mChildren;
    }

    @Override
    public void clearCount(String key) {
        //如果要清理的是当前parent的key
        if (this.key.equals(key)) {
            //清除自身的未读数
            clearCount(this);

            if (ListUtil.isNullOrEmpty(mChildren))
                return;
            //清楚child的未读数
            for (UnreadChild child : mChildren) {
                clearCount(child);
            }
        } else {
            for (UnreadChild child : mChildren) {
                //如果child 符合
                if (child.getKey().equals(key)) {
                    //清除child的未读数
                    clearCount(child);
                    //重新计算自身未读数
                    reCalculateCount();
                    return;
                }
            }
        }
    }

    /**
     * 重新计算自身未读数
     */
    public void reCalculateCount() {
        this.type = TYPE_NONE;
        this.count = 0;
        if (ListUtil.isNullOrEmpty(mChildren))
            return;
        for (UnreadChild child : mChildren) {
            if (child.type == TYPE_NONE)
                continue;
            if (child.type == TYPE_TEXT) {
                this.type = TYPE_TEXT;
                this.count += child.count;
            } else {
                if (this.type == TYPE_TEXT)
                    return;
                this.type = TYPE_POINT;
            }
        }
    }
}
