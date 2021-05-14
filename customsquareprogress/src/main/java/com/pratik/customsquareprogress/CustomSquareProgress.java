package com.pratik.customsquareprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomSquareProgress extends View {

    Path path = new Path();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float length;
    float[] intervals = {0, 0};
    int progress = 100, strokeWidth = 5;
    boolean shouldShowBackground = true;
    int radius = 10;

    String progressText;

    public CustomSquareProgress(Context context) {
        super(context);
        init();
    }

    public CustomSquareProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSquareProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setShouldShowBackground(boolean shouldShowBackground) {
        this.shouldShowBackground = shouldShowBackground;
    }

    void init() {
        paint.setColor(Color.parseColor("#FB2971"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        backgroundPaint.setColor(Color.parseColor("#DFDFDF"));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        PathEffect effect = new DashPathEffect(intervals, length - length * 1);
        backgroundPaint.setPathEffect(effect);

        PathEffect effect1 = new DashPathEffect(intervals, length - length * 1);
        paint.setPathEffect(effect1);
    }

    public void setPaintColor(String colorCodeWithHash) {
        paint.setColor(Color.parseColor(colorCodeWithHash));
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
        paint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStrokeWidth(strokeWidth);
    }

    public void setCornerRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        path.reset();
        RectF rect = new RectF(0, 0, w, h);
        float inset = paint.getStrokeWidth();
        rect.inset(inset, inset);

        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        length = new PathMeasure(path, false).getLength();
        intervals[0] = intervals[1] = length;
        PathEffect effect = new DashPathEffect(intervals, length);
        paint.setPathEffect(effect);
    }

    public void setProgress(int progress) {
        PathEffect effect = new DashPathEffect(intervals, length - length * progress / 100);
        paint.setPathEffect(effect);

        progressText = progress + "%";
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shouldShowBackground) {
            canvas.drawPath(path, backgroundPaint);
        }
        canvas.drawPath(path, paint);
    }
}