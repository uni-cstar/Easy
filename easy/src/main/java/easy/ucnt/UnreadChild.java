//package easy.ucnt;
//
//import java.util.List;
//
///**
// * Created by Lucio on 17/4/21.
// */
//
//public class UnreadChild extends Unread {
//
//    UnreadParent mParent;
//
//    public UnreadChild(UnreadEvent event, UnreadParent parent) {
//        super(event);
//        mParent = parent;
//    }
//
//    @Override
//    public UnreadParent getParent() {
//        return mParent;
//    }
//
//    @Override
//    public List<UnreadChild> getChildren() {
//        return null;
//    }
//
//    @Override
//    public void clearCount(String key) {
//        if (this.key.equals(key)) {
//            //清除自身的未读数
//            clearCount(this);
//            mParent.reCalculateCount();
//        }
//    }
//}
