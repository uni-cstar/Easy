package easy.view.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import easy.R;

/**
 * Created by Lucio on 17/4/18.
 */
public class EasyTabWidget extends LinearLayout {

    private Paint mDividerPaint;
    private int mDividerWidth = 1;

    public EasyTabWidget(Context context) {
        this(context, null);
    }

    public EasyTabWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDividerPaint = new TextPaint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(mDividerWidth);
        mDividerPaint.setStyle(Paint.Style.FILL);
        mDividerPaint.setColor(context.getResources().getColor(R.color.easy_tab_host_divider));
        setWillNotDraw(false);
    }

    /**
     * 设置分割线颜色
     *
     * @param color
     */
    public void setDividerColor(int color) {
        if (mDividerPaint == null)
            throw new RuntimeException("please call setup method first");
        mDividerPaint.setColor(color);
        if (this.getWidth() > 0) {
            this.postInvalidate(this.getLeft(), this.getTop(), this.getRight(), this.getTop() + mDividerWidth);
        } else {
            this.postInvalidate();
        }
        setWillNotDraw(false);
    }

    /**
     * 设置分割线粗细
     *
     * @param px
     */
    public void setDividerStroke(int px) {
        if (mDividerPaint == null)
            throw new RuntimeException("please call setup method first");
        mDividerWidth = Math.max(px, 0);
        mDividerPaint.setStrokeWidth(mDividerWidth);
        if (this.getWidth() > 0) {
            this.postInvalidate(this.getLeft(), this.getTop(), this.getRight(), this.getTop() + mDividerWidth);
        } else {
            this.postInvalidate();
        }
        setWillNotDraw(false);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //绘制分割线
        if (this.getWidth() > 0) {
            //绘制完成之后不再绘制
            canvas.drawLine(0, 0, this.getWidth(), 0, mDividerPaint);
            setWillNotDraw(true);
        }

    }

}
