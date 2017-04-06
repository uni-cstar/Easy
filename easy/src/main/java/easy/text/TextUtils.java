package easy.text;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.TextView;

/**
 * Created by Lucio on 17/3/13.
 */

public class TextUtils {

    /**
     * 设置密码输入的明文密文显示方式
     *
     * @param textView
     * @param visible  密码是否可见
     */
    public static void setPasswordShowType(TextView textView, boolean visible) {
        if (visible) {
            textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 设置密码输入的明文密文显示方式
     * PS:这种方式的弊端是会把原有的输入方式改为text，比如之前是number
     *
     * @param visible  是否明文
     * @deprecated {@link #setPasswordShowType(TextView, boolean)}
     */
    @Deprecated
    public static void setPasswordShowType2(TextView textView, boolean visible) {
        if (visible) {//显示明文--设置为可见的密码
            textView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {//密文显示--要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            textView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }


    /**
     * 设置文字阴影
     *
     * @param tv
     */
    public static void setTextShadow(TextView tv, int color) {
        tv.setShadowLayer(2, 0, 0, color);
    }

    /**
     * 设置文字阴影
     *
     * {@link TextView#setShadowLayer(float, float, float, int)}
     */
    public static void setTextShadow(TextView tv, float radius, float dx, float dy, int color) {
        tv.setShadowLayer(radius, dx, dy, color);

    }
}
