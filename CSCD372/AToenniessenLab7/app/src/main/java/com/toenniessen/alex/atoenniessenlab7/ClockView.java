package com.toenniessen.alex.atoenniessenlab7;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;


public class ClockView extends View {
    private final int containerHeight = 300, containerWidth = 300, clockRadius = 140;
    private final int originX = 150, originY = 150;
    private final int mHourDegrees = 360 / 12, mMinSecDegrees = 360 / 60;
    private final float[] hourHandXY = {originX, originY + 10, 160, 150, 150, 70, 140, 150};
    private final float[] minuteHandXY = {originX, originY + 5, 155, 150, 150, 30, 145, 150};
    private final float[] secondHandXY = {originX - 1, originY, originX + 1, originY,
            originX + 1, (float) (originY - (clockRadius * .96)), originX - 1, (float) (originY - (clockRadius * .96))};
    private Path mHourHand = new Path(), mMinuteHand = new Path(), mSecondHand = new Path();
    private final ViewableClock mTime = new ViewableClock(new GregorianCalendar());
    private MediaPlayer mMedia;
    private boolean mFormat, mPartial;
    private String mStyle;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClockView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    ViewableClock getmTime() {
        return mTime;
    }

    MediaPlayer getmMedia() {
        mMedia.seekTo(0);
        return mMedia;
    }

    void playClip() {
        if (mMedia != null){
            if(mMedia.isPlaying()) {
                mMedia.pause();
                mMedia.seekTo(0);
            }
            mMedia.start();
        } else {
            mMedia = MediaPlayer.create(getContext(), R.raw.click);
            mMedia.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                }
            });
            mMedia.setVolume(1f, 1f);
            mMedia.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int originalwidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight;
        if (originalwidth > originalHeight) {
            calculatedHeight = originalHeight;
        } else
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

        canvas.scale((float) getWidth() / containerWidth, (float) getHeight() / containerHeight);

        updateCanvas(canvas);
    }

    void updatePreferences(boolean format, boolean partial, String style) {
        mFormat = format;
        mPartial = partial;
        mStyle = style;
    }

    private void drawClockFace(Canvas canvas) {             //figure out why image resources dont draw properly
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.save();
        canvas.drawCircle(originX, originY, clockRadius, paint);
        canvas.restore();
        canvas.save();
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(originX, originY - clockRadius, originX, originY - (clockRadius - 5), paint);
            canvas.rotate(6, originX, originY);
            for (int j = 0; j < 4; j++) {
                canvas.drawLine(originX, originY - clockRadius, originX, originY - (clockRadius - 2), paint);
                canvas.rotate(6, originX, originY);
            }
        }
        canvas.restore();
    }

    private void drawCustomClockFace(Canvas canvas, String res) {
        Bitmap bm = null;
        switch (res) {
            case "1":
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.regular);
                break;
            case "2":
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.roman);
                break;
            case "3":
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
                break;

        }
        if (bm != null) {
            canvas.save();
            canvas.translate(10, 10);
            canvas.drawBitmap(bm, null,
                    new Rect(0, 0, clockRadius * 2, clockRadius * 2), null);
            canvas.restore();
            if (res.equals("3"))
                drawClockFace(canvas);
        }
        else
            drawClockFace(canvas);
    }

    private void updateCanvas(Canvas canvas) {
        if (mStyle.equals(getResources().getStringArray(R.array.clock_type_values)[0]))
            drawClockFace(canvas);
        else
            drawCustomClockFace(canvas, mStyle);
        mTime.setClock(new GregorianCalendar());

        float secondRotation = mMinSecDegrees * (mTime.getSecond() + (mTime.getMillisecond() / 1000)),
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

    class ViewableClock extends Observable {
        private float hour, minute, second, millisecond;
        private String am_pm = "";

        ViewableClock(GregorianCalendar date) {
            setClock(date);
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

        float getMillisecond() {
            return millisecond;
        }

        @Override
        public String toString() {
            return (int) hour + ":" + (int) minute + ":" + (int) second + ":" + ((mPartial) ? (int) millisecond / 100 : "") + ((mFormat) ? "" : am_pm);
        }


        void setClock(GregorianCalendar date) {
            hour = (mFormat) ? date.get(Calendar.HOUR_OF_DAY) : date.get(Calendar.HOUR);
            minute = date.get(Calendar.MINUTE);
            second = date.get(Calendar.SECOND);
            millisecond = date.get(Calendar.MILLISECOND);
            am_pm = (date.get(Calendar.AM_PM) == Calendar.AM) ? " AM" : " PM";
            setChanged();
            notifyObservers();
        }
    }
}
