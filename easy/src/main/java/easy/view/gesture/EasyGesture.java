package easy.view.gesture;

import android.view.View;

import easy.R;

/**
 * Created by Lucio on 17/4/13.
 * 简单的手势处理
 */

public class EasyGesture {

    /**
     * 双击手势回调
     */
    public interface OnEasyTapListener0 {
        boolean onEasyDoubleTaped();
    }

    /**
     * 双击和单击手势回调
     */
    public interface OnEasyTapListener extends OnEasyTapListener0 {
        boolean onEasySingleTaped();
    }

    /**
     * 添加Easy tap 手势
     * PS：如果同时设置了单击事件和点击手势，两者都会执行。
     * 因此如果使用此方法，则应该将点击事件的处理逻辑在点击手势事件中执行。
     * 如果要保留单击，和双击 {@link #addTapGesture(View, OnEasyTapListener0)}
     *
     * @param targetView 添加手势的控件
     * @param listener   回调
     */
    public static void addTapGesture(View targetView, OnEasyTapListener listener) {
        EasyTapGesture gesture = new EasyTapGesture(targetView);
        gesture.setOnEasyTapListener(listener);
        targetView.setTag(R.id.easy_tap_gesture_tag, gesture);
    }

    /**
     * 添加 Easy tap的双击手势处理
     *
     * @param view
     * @param listener
     */
    public static void addTapGesture(View view, OnEasyTapListener0 listener) {
        EasyTapGesture gesture = new EasyTapGesture(view);
        gesture.setOnEasyTapListener(listener);
        view.setTag(R.id.easy_tap_gesture_tag, gesture);
    }


    /**
     * 移除Easy tap 手势
     *
     * @param view
     */
    public static void removeTapGesture(View view) {
        Object obj = view.getTag(R.id.easy_tap_gesture_tag);
        if (obj == null || !(obj instanceof EasyTapGesture))
            return;
        view.setTag(R.id.easy_tap_gesture_tag, null);
    }

}
