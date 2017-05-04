//package easy.ucnt;
//
//import android.support.annotation.IntDef;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.util.List;
//
///**
// * Created by Lucio on 17/4/20.
// */
//
//public abstract class Unread implements UnreadEvent {
//
//    /**
//     * 没有未读数
//     */
//    public static final int TYPE_NONE = 0;
//
//    /**
//     * 数字
//     */
//    public static final int TYPE_TEXT = 1;
//
//    /**
//     * 红点
//     */
//    public static final int TYPE_POINT = 2;
//
//
//    UnreadEvent mEvent;
//
//    public Unread(UnreadEvent event) {
//        mEvent = event;
//    }
//
//    @Override
//    public void onUnreadCountChanged(Unread unread) {
//        mEvent.onUnreadCountChanged(unread);
//    }
//
//    protected String key;
//
//    protected int count;
//
//    @Type
//    protected int type;
//
//    public abstract UnreadParent getParent();
//
//    public abstract List<UnreadChild> getChildren();
//
//    public abstract void clearCount(String key);
//
//    /**
//     * 清除未读数
//     *
//     * @param unread
//     * @return true:有改变 false：otherwise
//     */
//    protected boolean clearCount(Unread unread) {
//        try {
//            if (unread.type == TYPE_NONE)
//                return false;
//            unread.type = TYPE_NONE;
//            unread.count = 0;
//            onUnreadCountChanged(this);
//            return true;
//        } finally {
//            unread.count = 0;
//            unread.type = TYPE_NONE;
//            return false;
//        }
//    }
//
//    @Type
//    public int getType() {
//        return type;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    /**
//     * 提醒类别
//     */
//    @IntDef({TYPE_NONE, TYPE_POINT, TYPE_TEXT})
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface Type {
//
//    }
//
//}
