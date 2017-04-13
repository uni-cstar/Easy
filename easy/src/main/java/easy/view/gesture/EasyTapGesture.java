package easy.view.gesture;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lucio on 17/4/13.
 * 简单的单击双击手势封装
 */
class EasyTapGesture extends GestureDetector implements GestureDetector.OnDoubleTapListener, View.OnTouchListener {


    //不包含单击的wrapper
    private static class SimpleEasyTapListener implements EasyGesture.OnEasyTapListener {

        EasyGesture.OnEasyTapListener0 mListener;

        private SimpleEasyTapListener(EasyGesture.OnEasyTapListener0 listener) {
            mListener = listener;
        }

        @Override
        public boolean onEasySingleTaped() {
            return false;
        }

        @Override
        public boolean onEasyDoubleTaped() {
            return mListener != null && mListener.onEasyDoubleTaped();
        }
    }

    EasyGesture.OnEasyTapListener mListener;

    public EasyTapGesture(View view) {
        this(view, new SimpleOnGestureListener(), null);
    }

    public EasyTapGesture(View view, OnGestureListener listener) {
        this(view, listener, null);
    }

    public EasyTapGesture(View view, OnGestureListener listener, Handler handler) {
        super(view.getContext(), listener, handler);
        this.setOnDoubleTapListener(this);
        view.setOnTouchListener(this);

    }

    public void setOnEasyTapListener(EasyGesture.OnEasyTapListener listener) {
        this.mListener = listener;
    }

    public void setOnEasyTapListener(EasyGesture.OnEasyTapListener0 listener) {
        this.mListener = new SimpleEasyTapListener(listener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (mListener != null)
            return mListener.onEasySingleTaped();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (mListener != null)
            return mListener.onEasyDoubleTaped();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }


}
