package be.newpage.milkyway.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import be.newpage.milkyway.R;
import roboguice.inject.InjectView;

public class MilkView extends FrameLayout implements View.OnTouchListener {
    private ImageView contents;
    private float value = 0;
    private int h = 0;
    private int w = 0;
    Paint p;

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

        contents = (ImageView) findViewById(R.id.contents);
        w = contents.getLayoutParams().width;
        h = contents.getLayoutParams().height;

        p = new Paint();
        p.setColor(Color.BLUE);

        contents.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawRect(0.0f,0.0f,(float)w,(value*h), p);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        float cropped = Math.max(0, Math.min(y, h));
        value = 1 - (cropped / ((float)h));
        Log.d("touch", "y: " + cropped + " value: " + value);

        invalidate();

        return true;
    }
}
