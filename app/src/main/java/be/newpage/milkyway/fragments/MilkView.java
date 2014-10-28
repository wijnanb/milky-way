package be.newpage.milkyway.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import be.newpage.milkyway.OnUpdateListener;
import be.newpage.milkyway.R;

public class MilkView extends ImageView implements View.OnTouchListener {
    private float value = 0;
    Paint p;
    private OnUpdateListener listener;

    public MilkView(Context context) {
        super(context);
        init();
    }

    public MilkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MilkView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setWillNotDraw(false);

        p = new Paint();
        p.setColor(getResources().getColor(R.color.beige_yellow));

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        float height = ((1 - value) * h);

        canvas.drawRect(0.0f, height, w, h, p);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float w = getWidth();
        float h = getHeight();

        float y = event.getY();
        float cropped = Math.max(0, Math.min(y, h));

        float v =  1 - (cropped / h);

        if (v != 0) {
            value = v;
            //Log.d("touch", "y: " + y + " value: " + value);

            if (listener != null) {
                listener.update(value);
            }

            invalidate();
        }
        return true;
    }

    public OnUpdateListener getListener() {
        return listener;
    }

    public void setListener(OnUpdateListener listener) {
        this.listener = listener;
    }
}
