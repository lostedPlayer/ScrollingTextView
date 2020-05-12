package com.gamadevelopment.scrolltextview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollTextView extends TextView implements Runnable {


    private static final float DEFAULT_SPEED = 15.0f;

    private Scroller scroller;
    private float speed = DEFAULT_SPEED;
    private boolean continuousScrolling = true;


    //constructors
    public ScrollTextView(Context context) {
        super(context);
        setup(context);
    }

    public ScrollTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setup(context);
    }


    private void setup(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        setScroller(scroller);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (scroller.isFinished()) {
            scroll();
        }
    }


    //start Scrolling method
    private void scroll() {
        int viewHeight = getHeight();
        int visibleHeight = viewHeight - getPaddingBottom() - getPaddingTop();
        int lineHeight = getLineHeight();

        int offset = -1 * visibleHeight;
        int distance = visibleHeight + getLineCount() * lineHeight;
        int duration = (int) (distance * speed);

        scroller.startScroll(0, offset, 0, distance, duration);

        if (continuousScrolling) {
            post(this);
        }
    }

    @Override
    public void run() {
        if (scroller.isFinished()) {
            scroll();
        } else {
            post(this);
        }
    }


    //set speed for Scrolling TextView
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    //get speed for Scrolling TextView
    public float getSpeed() {
        return speed;
    }

    //implementing continius scrolling of text
    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }


    //returns boolean state of scrolling text
    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }


    ///mirror tex
    public void mirrorTextOnn() {
        setScaleX(-1);
        setScaleY(1);
    }

    //reverse text to normal
    public void mirrorTextOff() {
        setScaleX(1);
    }
}
