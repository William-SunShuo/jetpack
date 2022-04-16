package com.example.jetpack1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    public static final String TAG="CustomView";
    private PorterDuff.Mode porterDuffMode = PorterDuff.Mode.SRC;
    private Paint mPaint= new Paint();

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setPorterDuffMode(PorterDuff.Mode porterDuffMode) {
        this.porterDuffMode = porterDuffMode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Bitmap dstBitmap = getDstBitmap(200, 200);
        Bitmap srcBitmap = getSrcBitmap(200, 200);

        //设置离屏缓冲
        int saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(dstBitmap,100,100,mPaint);
        if (porterDuffMode!=null) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        canvas.drawBitmap(srcBitmap,100,100,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }


    private Bitmap getDstBitmap(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30f);
        Path path = new Path();
        path.moveTo(20f,20f);
        path.lineTo(100f,100f);
//        canvas.drawCircle(w/2,h/2,w/3,paint);
        canvas.drawPath(path,paint);
        return bitmap;
    }

    private Bitmap getSrcBitmap(int w,int h){
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30f);
        Path path = new Path();
        path.moveTo(100f,20f);
        path.lineTo(20f,100f);
//        canvas.drawCircle(w/2,h/2,w/3,paint);
        canvas.drawPath(path,paint);
//        canvas.drawRect(new Rect(0,0,w*2/3,h*2/3),paint);
        return bitmap;
    }
}