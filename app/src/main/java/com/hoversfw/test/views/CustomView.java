package com.hoversfw.test.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private Rect rect;
    private Paint paint;
    private Paint circlePaint;
    private float radius=100f;
    private float circleX=300f;
    private float circleY=300f;

    public CustomView(Context context) {
        super(context);

        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(@Nullable AttributeSet set){
        rect=new Rect();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
    }

    public void swap(){
        if(paint.getColor()==Color.GREEN){
            paint.setColor(Color.RED);
            Log.d("CustomView","Swapped to red");
        }
        else if(paint.getColor()==Color.RED){
            paint.setColor(Color.GREEN);
            Log.d("CustomView","Swapped to green");
        }
        radius=100f;
        postInvalidate();
        Log.d("CustomView","Done");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value=super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                float x=event.getX();
                float y=event.getY();
                double dx=Math.pow(x-circleX,2);
                double dy=Math.pow(y-circleY,2);
                if(dx+dy<=Math.pow(radius,2)){
                    circleX=x;
                    circleY=y;
                    postInvalidate();
                    return true;
                }
                return true;
            }
            case MotionEvent.ACTION_UP:{
                return true;
            }
        }
        return value;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.left=50;
        rect.top=50;
        rect.bottom=rect.top+200;
        rect.right=rect.left+200;
        canvas.drawRect(rect,paint);
        canvas.drawCircle(circleX,circleY,radius,circlePaint);
    }
}
