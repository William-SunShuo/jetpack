package com.example.jetpack1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.style.ReplacementSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * Created by shichen.shen on 2019/7/10.
 */

public class BorderSpan extends ReplacementSpan {
    private int mWidth;
    private int mBackgroundColor;
    private Path path = new Path();

    public BorderSpan(@ColorInt int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {
        if (mBackgroundColor != 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mBackgroundColor);
            canvas.drawRect(x, top, x + mWidth, y + paint.getFontMetrics().bottom, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(text, start, end, x, y, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(2);
            path.moveTo(x, bottom - 5);
            path.quadTo(x + 10, bottom - 10, x + 20, bottom - 5);
            path.quadTo(x + 30, bottom, x + 40, bottom - 5);
            path.quadTo(x + 50, bottom - 10, x + 60, bottom - 5);
            path.quadTo(x + 70, bottom, x + 80, bottom - 5);
            path.quadTo(x + 90, bottom - 10, x + 100, bottom - 5);
            canvas.drawPath(path, paint);
        }
    }
}
