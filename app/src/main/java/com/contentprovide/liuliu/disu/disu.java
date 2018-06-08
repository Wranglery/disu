package com.contentprovide.liuliu.disu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuliu on 2018/6/7.
 */

public class disu extends View {

    Paint paint;

    int x = 0;
    int y = 0;

    public disu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.a2);
        canvas.drawBitmap(bitmap,x,y,paint);
    }


}
