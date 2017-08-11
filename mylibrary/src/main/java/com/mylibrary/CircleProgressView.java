package com.mylibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.mylibrary.utils.DensityUtil;

/**
 * Created by ysj on 2017/8/10.
 */

public class CircleProgressView extends View {
    private Paint mCirPaint;
    private Paint mArcPaint;
    //
    private Paint mTextCenterPaint;
    private Paint mTextTopFloorPaint;
    private Paint mTextTopPaint;
    private Paint mTextBottomPaint;
    //
    private int mTextCenterSize;
    private int mTextTopSize;
    private int mTextTopFloorSize;
    private int mTextBottomSize;

    private float radius = 200;
    private int progress;
    private int stokeWidth = 10;
    private int circleColor = Color.GRAY;

    private int reachColor = Color.GREEN;
    private int speed = 10;

    private String centerText;
    private String topText;
    private String topFloorText;

    private int topFloorTextColor;
    private int topTextColor;
    private int centerTextColor;
    private int bottomTextColor;

    private String bottomText;

    private  Context mContext;

    public CircleProgressView(Context context) {
        super(context);
        this.mContext=context;
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorProgressView);
        mTextTopFloorSize=typedArray.getInteger(R.styleable.ColorProgressView_topFloorTextSize,DensityUtil.dip2px(mContext,10));
        mTextTopSize=typedArray.getInteger(R.styleable.ColorProgressView_topTextSize,DensityUtil.dip2px(mContext,14));
        mTextCenterSize=typedArray.getInteger(R.styleable.ColorProgressView_centerTextSize,DensityUtil.dip2px(mContext,20));
        mTextBottomSize=typedArray.getInteger(R.styleable.ColorProgressView_bottomTextSize,DensityUtil.dip2px(mContext,14));
        topFloorTextColor=typedArray.getColor(R.styleable.ColorProgressView_topFloorTextColor,Color.BLUE);
        topTextColor=typedArray.getColor(R.styleable.ColorProgressView_topTextColor,Color.RED);
        centerTextColor=typedArray.getInteger(R.styleable.ColorProgressView_centerTextColor,Color.RED);
        bottomTextColor=typedArray.getColor(R.styleable.ColorProgressView_bottomTextColor,Color.BLUE);
        reachColor=typedArray.getColor(R.styleable.ColorProgressView_reachColor,Color.BLUE);

        stokeWidth=typedArray.getInteger(R.styleable.ColorProgressView_stokeWith,DensityUtil.dip2px(mContext,6));
        typedArray.recycle();
        init();
    }

    public void setTopFloorText(String topFloorText) {
        this.topFloorText = topFloorText;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public void setCenterText(String centerText) {
        this.centerText = centerText;
        invalidate();
    }

    public void setTopText(String topText) {
        this.topText = topText;
        invalidate();
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
        invalidate();
    }

    //进度速度
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    private void init() {
        //画最外层圆
        mCirPaint = new Paint();
        mCirPaint.setColor(circleColor);
        mCirPaint.setAntiAlias(true);
        mCirPaint.setStyle(Paint.Style.STROKE);
        mCirPaint.setStrokeWidth(stokeWidth);

        //进度弧度
        mArcPaint = new Paint();
        mArcPaint.setColor(reachColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(stokeWidth);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        //字体画笔 中间文字
        mTextCenterPaint = new Paint();
        mTextCenterPaint.setColor(centerTextColor);
        mTextCenterPaint.setTextSize(mTextCenterSize);
        mTextCenterPaint.setAntiAlias(true);

        //最上方小文字
        mTextTopFloorPaint=new Paint();
        mTextTopFloorPaint.setColor(topFloorTextColor);
        mTextTopFloorPaint.setTextSize(mTextTopFloorSize);
        mTextTopFloorPaint.setAntiAlias(true);

        //上方文字
        mTextTopPaint = new Paint();
        mTextTopPaint.setColor(topTextColor);
        mTextTopPaint.setTextSize(mTextTopSize);
        mTextTopPaint.setAntiAlias(true);
        //底部文字
        mTextBottomPaint = new Paint();
        mTextBottomPaint.setColor(bottomTextColor);
        mTextBottomPaint.setTextSize(mTextBottomSize);
        mTextBottomPaint.setAntiAlias(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        radius = centerX-stokeWidth/2;
        canvas.drawCircle(centerX, centerY, radius, mCirPaint);
        //画圆
        RectF rectF=new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, -90, progress * 360 / 100, false, mArcPaint);

        //设置中心文本
        if (null != centerText) {
            canvas.drawText(centerText, centerX - (mTextCenterPaint.measureText(centerText)) / 2, centerY + mTextCenterSize / 2, mTextCenterPaint);
        }
        //上方文字
        if (null != topText) {
            canvas.drawText(topText, centerX - (mTextTopPaint.measureText(topText)) / 2, centerY - mTextCenterSize, mTextTopPaint);
        }
        //最上方小文字
        if(null !=topFloorText){
            canvas.drawText(topFloorText,getWidth()-mTextCenterPaint.measureText(topFloorText),centerY-mTextCenterSize-mTextTopSize, mTextTopFloorPaint);
        }
        if (null != bottomText) {
            //下方圆角矩形
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(bottomTextColor);
            RectF rec = new RectF(centerX - (mTextBottomPaint.measureText(bottomText)) / 2-DensityUtil.dip2px(mContext,6),
                    getHeight()-centerY/2-mTextBottomSize,
                    centerX +(mTextBottomPaint.measureText(bottomText)) / 2+DensityUtil.dip2px(mContext,6),
                    getHeight()-(centerY)/2+mTextBottomSize/2);
            canvas.drawRoundRect(rec,10, 10, paint);    //第二个参数是x半径，第三个参数是y半径
            //下方文字
            canvas.drawText(bottomText,centerX - (mTextBottomPaint.measureText(bottomText)) / 2, getHeight()-(centerY)/2, mTextBottomPaint);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (speed != 0) {
            startProgress();
        }

    }
    public void startProgress() {
        final int preProgress = progress;
        new CountDownTimer(preProgress * speed, speed) {
            @Override
            public void onTick(long l) {
                setProgress(preProgress - (int) (l / speed));
                invalidate();
            }

            @Override
            public void onFinish() {
                setProgress(preProgress);
                invalidate();
                this.cancel();
            }
        }.start();
    }
}
