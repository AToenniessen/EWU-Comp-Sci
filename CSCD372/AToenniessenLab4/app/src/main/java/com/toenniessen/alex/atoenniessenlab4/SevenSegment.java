package com.toenniessen.alex.atoenniessenlab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class SevenSegment extends View {
    private int mCurrentDisplay;
    private boolean[] mSegmentState = new boolean[7];
    private static final float[] mXYVerticies = {1, 2, 2, 3, 20, 3, 21, 2, 20, 1, 2, 1};
    private Path mPath = new Path();
    private int mOn = Color.argb(255,255,0,0);
    private int mOff = Color.argb(50, 255, 0, 0);

    public SevenSegment(Context context){
        super(context);
    }
    public SevenSegment(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }
    public SevenSegment(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawRGB(0, 0, 0);
        int w = getWidth();
        int h = getHeight();
        mPath.moveTo( mXYVerticies[0], mXYVerticies[1]);
        mPath.lineTo( mXYVerticies[2], mXYVerticies[3]);
        mPath.lineTo( mXYVerticies[4], mXYVerticies[5]);
        mPath.lineTo( mXYVerticies[6], mXYVerticies[7]);
        mPath.lineTo( mXYVerticies[8], mXYVerticies[9]);
        mPath.lineTo( mXYVerticies[10], mXYVerticies[11]);
        mPath.close();
        canvas.scale((float) w/22, (float) h/60);
        canvas.clipPath(mPath);
        canvas.drawColor(mOn);

    }
}
