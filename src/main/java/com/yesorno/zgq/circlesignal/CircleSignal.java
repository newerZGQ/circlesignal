package com.yesorno.zgq.circlesignal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 37902 on 2016/4/3.
 */
public class CircleSignal extends View {
    private static final int MODEONESIGN = 1;
    private static final int MODETWOSIGN = 2;
    private static final int MODETHRSIGN = 3;
    private static final int MODEFOUSIGN = 4;
    private int mode = MODEONESIGN;
    private String firstSign = "1";
    private String secodSign = "2";
    private String thirdSign = "3";
    private String forthSign = "4";
    private float textsize = 15;
    private int textColor = Color.parseColor("#696969");
    private int circleColor = Color.parseColor("#FFFFFF");
    private Paint circlePaint;
    private Paint signPaint;
    private int textOffsetAngle;
    private RectF rectF;
    private int textoffset;
    private Context context;

    public CircleSignal(Context context) {
        super(context);
        this.context = context;
    }

    public CircleSignal(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SignOfCircle);
        this.context = context;
        this.mode = array.getInt(R.styleable.SignOfCircle_mode, MODEONESIGN);
        this.firstSign = array.getString(R.styleable.SignOfCircle_first_sign);
        this.secodSign = array.getString(R.styleable.SignOfCircle_second_sign);
        this.thirdSign = array.getString(R.styleable.SignOfCircle_third_sign);
        this.forthSign = array.getString(R.styleable.SignOfCircle_forth_sign);
        this.textsize = array.getDimension(R.styleable.SignOfCircle_text_size, (float) 10);
        this.textColor = array.getColor(R.styleable.SignOfCircle_text_color, Color.GREEN);
        this.circleColor = array.getColor(R.styleable.SignOfCircle_cirlce_color, Color.GREEN);
        this.signPaint = new Paint();
        this.circlePaint = new Paint();
        rectF = new RectF();
        initPaint();
    }

    private void initPaint() {
        signPaint.setAntiAlias(true);
        signPaint.setColor(textColor);
        signPaint.setTextSize(textsize);
        signPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textOffsetAngle = (int) ((Math.atan(textsize * 3 / getWidth())) * 180 / Math.PI);
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - (int) textsize * 2;
        int textBaseLineOffset = (int) textsize / 3;
        rectF.set((float) (center - radius), (float) (center - radius), (float) (center + radius), (float) (center + radius));
        switch (mode) {
            case MODEONESIGN:
                //the forth argument means if 封口，false 是 不封口，意思就是不画中间
                canvas.drawArc(rectF, 0, 270 - textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 270 + textOffsetAngle, 90, false, circlePaint);
                Paint.FontMetricsInt fontMetrics = signPaint.getFontMetricsInt();
                int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                canvas.drawText(firstSign, center, center - radius - textsize / 2, signPaint);
                break;
            case MODETWOSIGN:
            case MODETHRSIGN:
                canvas.drawArc(rectF, 30 + textOffsetAngle, 120 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 150 + textOffsetAngle, 120 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 270 + textOffsetAngle, 120 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawText(firstSign, center, center - radius + textBaseLineOffset, signPaint);
                int xoffset = (int) ((radius) * Math.acos((Math.PI / 6))) - textBaseLineOffset;
                int yoffset = (int) ((radius) * Math.asin((Math.PI / 6)));
                canvas.drawText(thirdSign, center - xoffset, center + yoffset + textBaseLineOffset, signPaint);
                canvas.drawText(secodSign, center + xoffset, center + yoffset + textBaseLineOffset, signPaint);
                break;
            case MODEFOUSIGN:
                canvas.drawArc(rectF, 0 + textOffsetAngle, 90 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 90 + textOffsetAngle, 90 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 180 + textOffsetAngle, 90 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawArc(rectF, 270 + textOffsetAngle, 90 - 2 * textOffsetAngle, false, circlePaint);
                canvas.drawText(firstSign, center, center - radius + textBaseLineOffset, signPaint);
                canvas.drawText(secodSign, center + radius, center + textBaseLineOffset, signPaint);
                canvas.drawText(thirdSign, center, center + radius + textBaseLineOffset, signPaint);
                canvas.drawText(forthSign, center - radius, center + textBaseLineOffset, signPaint);
                break;
        }
    }
}

