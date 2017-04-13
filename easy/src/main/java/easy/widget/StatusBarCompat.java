package easy.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import easy.R;


/**
 * Created by Lucio on 17/3/20.
 * 透明状态栏实现类
 */
public final class StatusBarCompat {


    interface StatusBarCompatImpl {

        /**
         * 设置透明状态栏样式
         *
         * @param activity
         */
        void setTransparentBarStyle(Activity activity);

        /**
         * 设置状态栏颜色
         *
         * @param activity
         * @param color
         */
        void setStatusBarColor(Activity activity, int color);


        /**
         * 设置透明状态栏
         * 适合背景是图片的情况
         */
        void setTransparentBarForImage(Activity activity, View... offsetViews);


    }

    //缺省实现
    private static class StatusBarCompatDef implements StatusBarCompatImpl {

        static final int TAG_KEY_OFFSET_FLAG = -1111;

        @Override
        public void setTransparentBarStyle(Activity activity) {

        }

        @Override
        public void setStatusBarColor(Activity activity, int color) {

        }

        @Override
        public void setTransparentBarForImage(Activity activity, View... offsetViews) {

        }

        void offsetView(View view) {
            Object haveSetOffset = view.getTag(TAG_KEY_OFFSET_FLAG);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(view.getContext()),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            view.setTag(TAG_KEY_OFFSET_FLAG, true);
        }
    }

    /**
     * 修正android.R.id.content包含的布局参数
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void fixContentGroupParams(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static class StatusBarCompat19 extends StatusBarCompatDef {

        /**
         * 自定义状态栏ID
         */

        static final int FAKE_STATUS_BAR_ID_19 = R.id.fake_status_bar_id_19;

        /**
         * 生成一个和状态栏大小相同的view
         *
         * @param activity 需要设置的activity
         * @param color    状态栏颜色值
         * @return 状态栏矩形条
         */
        private static View createFakeStatusBarView(Activity activity, @ColorInt int color) {
            // 绘制一个和状态栏一样高的矩形
            View statusBarView = new View(activity);
            int statusBarHeight = getStatusBarHeight(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setLayoutParams(params);
            statusBarView.setBackgroundColor(color);
            statusBarView.setId(FAKE_STATUS_BAR_ID_19);
            return statusBarView;
        }

        @Override
        public void setTransparentBarStyle(Activity activity) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        /**
         * 4.4添加一个根系统状态栏一样的view充当状态栏
         *
         * @param activity
         * @param color
         */
        @Override
        public void setStatusBarColor(Activity activity, int color) {
            setTransparentBarStyle(activity);
            ensureFakeStatusBar(activity, color);
            fixContentGroupParams(activity);
        }


        /**
         * 确保'伪装'状态栏
         *
         * @param activity
         * @param color    状态栏颜色
         */
        private static void ensureFakeStatusBar(Activity activity, int color) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_ID_19);
            //如果已经包含了'伪装'状态栏，则直接显示
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == View.GONE) {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(color);
            } else {
                //添加一个'伪装'状态栏
                fakeStatusBarView = createFakeStatusBarView(activity, color);
                decorView.addView(fakeStatusBarView);
            }
        }

        /**
         * 适合跟布局背景是图片的情况
         *
         * @param activity
         */
        @Override
        public void setTransparentBarForImage(Activity activity, View... offsetViews) {
            setTransparentBarStyle(activity);
            if (offsetViews != null && offsetViews.length > 0) {
                for (View item : offsetViews) {
                    offsetView(item);
                }
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class StatusBarCompat21 extends StatusBarCompatDef {

        /**
         * @param activity
         */
        @Override
        public void setTransparentBarStyle(Activity activity) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        @Override
        public void setStatusBarColor(Activity activity, int color) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);
        }


        /**
         * 适合根布局背景是图片的情况
         *
         * @param activity
         */
        @Override
        public void setTransparentBarForImage(Activity activity, View... offsetViews) {
            setTransparentBarStyle(activity);
            if (offsetViews != null && offsetViews.length > 0) {
                for (View item : offsetViews) {
                    offsetView(item);
                }
            }
        }
    }

    private static final StatusBarCompatImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {//5.x
            IMPL = new StatusBarCompat21();
        } else if (version >= Build.VERSION_CODES.KITKAT) { // 4.4
            IMPL = new StatusBarCompat19();
        } else {
            IMPL = new StatusBarCompatDef();
        }
    }

    /**
     * 获得状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 设置透明状态栏样式
     *
     * @param activity
     */
    public static void setTransparentBarStyle(Activity activity) {
        IMPL.setTransparentBarStyle(activity);
    }

    /**
     * 设置主题色状态栏
     *
     * @param activity
     */
    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity, activity.getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        IMPL.setStatusBarColor(activity, color);
    }

    /**
     * 设置透明状态栏
     *
     * @param activity
     */
    public static void setTransparentBarForImage(Activity activity, View... offsetViews) {
        IMPL.setTransparentBarForImage(activity, offsetViews);
    }

}
