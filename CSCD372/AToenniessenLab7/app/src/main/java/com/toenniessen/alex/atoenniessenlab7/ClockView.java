package com.toenniessen.alex.atoenniessenlab7;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;


public class ClockView extends View{
    private final int containerHeight = 300, containerWidth = 300, clockRadius = 140;
    private final int originX = 150, originY = 150;
    private final int mHourDegrees = 360/12, mMinSecDegrees = 360/60;
    private final float[] hourHandXY = {originX,originY + 10,160,150,150,70,140,150};
    private final float[] minuteHandXY = {originX,originY + 5,155,150,150,30,145,150};
    private final float[] secondHandXY = {originX - 1, originY, originX + 1, originY,
            originX + 1, (float) (originY - (clockRadius * .96)), originX - 1, (float) (originY - (clockRadius * .96))};
    private TimeAnimator mTimer;
    private Path mHourHand = new Path(), mMinuteHand = new Path(), mSecondHand = new Path();
    private final ViewableClock mTime = new ViewableClock(new GregorianCalendar());
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
    public ViewableClock getmTime(){
        return mTime;
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
        int originalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight;
        if(originalwidth > originalHeight){
            calculatedHeight = originalHeight;
        }
        else
            calculatedHeight = originalwidth;
        setMeasuredDimension(calculatedHeight, calculatedHeight);
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
    private void drawClockFace(Canvas canvas){      //start here bitch
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.save();
        canvas.drawCircle(originX, originY, clockRadius, paint);
        canvas.restore();
        canvas.save();
        for(int i = 0; i < 12; i++){
            canvas.drawLine(originX, originY - clockRadius, originX, originY - (clockRadius - 5), paint);
            canvas.rotate(6, originX, originY);
            for(int j = 0; j < 4; j++){
                canvas.drawLine(originX, originY - clockRadius, originX, originY - (clockRadius - 2), paint);
                canvas.rotate(6, originX, originY);
            }
        }
        canvas.restore();
    }
    private void updateCanvas(Canvas canvas){
        drawClockFace(canvas);
        mTime.setClock(new GregorianCalendar());

        float secondRotation = mMinSecDegrees * mTime.getSecond(),
                minuteRotation = (mMinSecDegrees * mTime.getMinute()) + (mMinSecDegrees * (secondRotation / 360)),
                hourRotation = (mHourDegrees * mTime.getHour()) + (mHourDegrees * (minuteRotation / 360));

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
    class ViewableClock extends Observable{
        private float hour, minute, second;
        ViewableClock(GregorianCalendar date){
            hour = date.get(Calendar.HOUR_OF_DAY);
            minute = date.get(Calendar.MINUTE);
            second = date.get(Calendar.SECOND);
        }

        float getHour() {
            return hour;
        }

        float getMinute() {
            return minute;
        }

        float getSecond() {
            return second;
        }
        @Override
        public String toString(){
            return (int)hour + ":" + (int)minute + ":" + (int)second;
        }

        void setClock(GregorianCalendar date){
            hour = date.get(Calendar.HOUR_OF_DAY);
            minute = date.get(Calendar.MINUTE);
            second = date.get(Calendar.SECOND);
            setChanged();
            notifyObservers();
        }
    }
}
