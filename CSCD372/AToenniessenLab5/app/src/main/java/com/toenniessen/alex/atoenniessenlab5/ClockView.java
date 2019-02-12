package com.toenniessen.alex.atoenniessenlab5;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ClockView extends View{
    private final int containerHeight = 300, containerWidth = 300, clockRadius = 140;
    private final int originX = 150, originY = 150;
    private final int mHourDegrees = 360/12, mMinSecDegrees = 360/60;
    private final float[] hourHandXY = {originX,originY + 10,160,150,150,70,140,150};
    private final float[] minuteHandXY = {originX,originY + 5,155,150,150,30,145,150};
    private final float[] secondHandXY = {originX - 1, originY, originX + 1, originY,
            originX + 1, (float) (originY - (clockRadius * .97)), originX - 1, (float) (originY - (clockRadius * .97))};
    private TimeAnimator mTimer;
    private Path mHourHand = new Path(), mMinuteHand = new Path(), mSecondHand = new Path();
    public ClockView(Context context) {
        super(context);
        initializeTimer();
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeTimer();
    }

    public ClockView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initializeTimer();
    }
    private void initializeTimer(){
        mTimer = new TimeAnimator();
        mTimer.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                invalidate();
            }
        });
        mTimer.start();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight = originalwidth * containerHeight / containerWidth;
        if(calculatedHeight > originalHeight){
            setMeasuredDimension(calculatedHeight * originalwidth / originalHeight, originalHeight);
        }
        else
            setMeasuredDimension(originalwidth, calculatedHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);


        mSecondHand.moveTo(secondHandXY[0], secondHandXY[1]);
        mSecondHand.lineTo(secondHandXY[2], secondHandXY[3]);
        mSecondHand.lineTo(secondHandXY[4], secondHandXY[5]);
        mSecondHand.lineTo(secondHandXY[6], secondHandXY[7]);

        mSecondHand.close();

        mHourHand.moveTo(hourHandXY[0], hourHandXY[1]);
        mHourHand.lineTo(hourHandXY[2], hourHandXY[3]);
        mHourHand.lineTo(hourHandXY[4], hourHandXY[5]);
        mHourHand.lineTo(hourHandXY[6], hourHandXY[7]);
        mHourHand.close();

        mMinuteHand.moveTo(minuteHandXY[0], minuteHandXY[1]);
        mMinuteHand.lineTo(minuteHandXY[2], minuteHandXY[3]);
        mMinuteHand.lineTo(minuteHandXY[4], minuteHandXY[5]);
        mMinuteHand.lineTo(minuteHandXY[6], minuteHandXY[7]);
        mMinuteHand.close();

        int w = getWidth();
        int h = getHeight();
        canvas.scale((float) w / containerWidth, (float) h / containerHeight);

        updateCanvas(canvas);
    }
    private void drawClockFace(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.save();
        canvas.drawCircle(originX, originY, clockRadius, paint);
        canvas.restore();
    }
    private void updateCanvas(Canvas canvas){
        drawClockFace(canvas);
        GregorianCalendar date = new GregorianCalendar();
        int hour = date.get(Calendar.HOUR_OF_DAY), minute = date.get(Calendar.MINUTE), second = date.get(Calendar.SECOND);
        float secondRotation = mMinSecDegrees * second,
                minuteRotation = (mMinSecDegrees * minute) + (mMinSecDegrees * (secondRotation / 360)),
                hourRotation = mHourDegrees * hour;

        drawHand(canvas, mSecondHand, secondRotation);
        drawHand(canvas, mMinuteHand, minuteRotation);
        drawHand(canvas, mHourHand, hourRotation);
    }
    private void drawHand(Canvas canvas, Path path, float rotation) {
        canvas.save();
        canvas.rotate(rotation, originX, originY);
        canvas.clipPath(path);
        canvas.drawColor(Color.argb(255, 0, 0, 0));
        canvas.restore();
    }
}
