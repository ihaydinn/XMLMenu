package com.ismailhakkiaydin.xmlmenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class ToDoListItemView extends android.support.v7.widget.AppCompatTextView {

    private Paint linePaint, marginPaint;
    private int paperColor;
    private float margin;

    private void init(){
        Resources res = getResources();
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(res.getColor(R.color.notepadMargin));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(res.getColor(R.color.notepadLines));

        paperColor = res.getColor(R.color.notepadPaper);
        margin = res.getDimension(R.dimen.notepadMargin);
    }

    public ToDoListItemView(Context context) {
        super(context);
        init();
    }

    public ToDoListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToDoListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(paperColor);
        canvas.drawLine(0,0,getMeasuredHeight(),0,linePaint);
        canvas.drawLine(0,getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(),linePaint);
        canvas.drawLine(margin,0,margin,getMeasuredHeight(),marginPaint);
        canvas.save();
        canvas.translate(margin,0);

        super.onDraw(canvas);
        canvas.restore();
    }
}