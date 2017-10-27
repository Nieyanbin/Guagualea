package com.example.dell.guaguale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dell on 2017/10/27.
 * 作者 聂
 * 自定义view
 */

public class GuaGuaKa extends View {

    private Paint pathPaint;

    private Path path;

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;

    private Bitmap backBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nyb);
    private float pointerX;
    private float pointerY;

    public GuaGuaKa(Context context) {
        this(context, null);
    }

    public GuaGuaKa(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.WHITE);

        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setDither(true);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(50);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        cacheBitmap = Bitmap.createBitmap(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingTop() - getPaddingBottom(), Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerX = event.getX();
                pointerY = event.getY();
                path.moveTo(pointerX, pointerY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                pointerX = event.getX();
                pointerY = event.getY();
                break;
        }
        invalidate();
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        canvas.drawBitmap(backBitmap, 0, 0, null);
        //利用PorterDuff.Mode画线条，重叠模式为DST_OUT
        //灰色背景和path的重叠
        cacheCanvas.drawColor(Color.GRAY);
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        cacheCanvas.drawPath(path, pathPaint);
        //画前景
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
    }

}