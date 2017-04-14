package easy.view.gesture;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lucio on 17/4/13.
 * 按两次退出程序
 */
public class EasyDoubleBackToExit {

    Activity mActivity;
    //上一次点击时间
    private long mLastClickTime = 0;
    private String mToastContent;
    int mBackType = Type.FINISH;
    //间隔时间
    private long mIntervalTime;

    static final String TOAST_FINISH = "再按一次退出程序";
    static final String TOAST_MOVE_TO_BACK = "再按一次回到桌面";


    OnBackPressedListener mListener;

    public EasyDoubleBackToExit(Activity activity, @Type int type) {
        mActivity = activity;
        mIntervalTime = 2000;
        mToastContent = type == Type.FINISH ? TOAST_FINISH : TOAST_MOVE_TO_BACK;
        mBackType = type;
    }

    /**
     * 设置间隔时间
     * 默认2000ms
     *
     * @param time 时间 ms
     * @return
     */
    public EasyDoubleBackToExit setIntervalTime(long time) {
        mIntervalTime = time;
        return this;
    }

    /**
     * 设置提示内容
     * 默认{@link #TOAST_FINISH} or {@link #TOAST_MOVE_TO_BACK}
     *
     * @param content
     * @return
     */
    public EasyDoubleBackToExit setToastContent(String content) {
        mToastContent = content;
        return this;
    }

    /**
     * 设置第一次按返回键处理回调
     *
     * @param listener
     * @return
     */
    public EasyDoubleBackToExit setOnBackPressedListener(OnBackPressedListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 点击返回键处理
     * {@link Activity#onBackPressed()}
     */
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTime < mIntervalTime) {
            mLastClickTime = 0;
            if (mBackType == Type.MOVE_TO_BACK) {
                mActivity.moveTaskToBack(true);
            } else {
                mActivity.finish();
            }
        } else {
            mLastClickTime = System.currentTimeMillis();
            if (mListener == null || !mListener.onFirstBackPressed()) {
                Toast.makeText(mActivity, mToastContent, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 回调接口
     */
    public interface OnBackPressedListener {
        /**
         * 第一次触发按返回键
         *
         * @return true:消耗了事件  false：相反
         */
        boolean onFirstBackPressed();
    }

    /**
     * 退出类型
     */
    @IntDef({EasyDoubleBackToExit.Type.FINISH, EasyDoubleBackToExit.Type.MOVE_TO_BACK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {

        /**
         * 退出
         */
        int FINISH = 0;

        /**
         * 移到后台
         */
        int MOVE_TO_BACK = 1;
    }

}
