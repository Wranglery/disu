package com.contentprovide.liuliu.disu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/*
* 实现步骤：
*   步骤一：
*   给开始按钮添加点击事件，点击按钮之后通过Handler对象给Handler发送消息让线程开始倒计时，
*   发10次，每次延迟1秒
*   步骤二：
*   对地鼠图片进行触摸点击处理，当点击的位置在地鼠图片的范围内时地鼠消失在其他地方重新出现，分数相对应的增加
*   步骤三：
*   当倒计时结束弹出对话框提示用户游戏结束同时显示出分数
*
*   案例比较简单，很多功能没有优化和实现，比如说没有写重新开始的功能
*
*
*
* */



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start_btn;
    TextView alltime, count_time;
    disu di;
    int count = 10;
    int get_x, get_y;

    DisplayMetrics dm;

    Bitmap bit;
    int bit_x;
    int bit_y;

    int fenshu = 0;

    Activity activity;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

//            开始游戏、alltime开始倒数60s
            switch (msg.what) {
                case 1:
//                    显示倒计时时间
                    alltime.setText(count + "");
                    if (count == 10) {
                        get_x = (int) (Math.random() * (dm.widthPixels - bit_x));
                        get_y = (int) (Math.random() * (dm.heightPixels - bit_y - 200)) + 200;
                        di.x = get_x;
                        di.y = get_y;
                        di.setVisibility(View.VISIBLE);
                    }
                    if (count == 0) {
                        new AlertDialog.Builder(activity)
                                .setTitle("游戏结束")
                                .setMessage("你挂了,你的得分是："+fenshu)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create().show();
                    } else if (count > 0) {
                        count--;
                    }

                    break;
                case 2://地鼠被点击后消失，分数增加
                    di.setVisibility(View.GONE);
                    fenshu += 10;
                    count_time.setText(fenshu + "");
                    handler.sendEmptyMessage(3);
                    break;
                case 3://地鼠被点击后重新在其他地方出现
                    try {
                        Thread.sleep(500);
                        get_x = (int) (Math.random() * (dm.widthPixels - bit_x));
                        get_y = (int) (Math.random() * (dm.heightPixels - bit_y - 200)) + 200;
                        di.x = get_x;
                        di.y = get_y;
                        di.setVisibility(View.VISIBLE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        获得地鼠图片大小
        bit_x = bit.getWidth();
        bit_y = bit.getHeight();
//        设置地鼠初始不可见
        di.setVisibility(View.GONE);





    }

    //    处理触摸事件，点击范围在地鼠图片上时发送一个消息给线程进行下一步操作
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int ev_x = (int) event.getX();
        int ev_y = (int) event.getY();

        if (ev_x > get_x && ev_x < get_x + bit_x &&
                ev_y > get_y && ev_y < get_y + bit_y) {
            handler.sendEmptyMessage(2);
        }

        return super.onTouchEvent(event);
    }


    //    初始化控件
    public void init() {

//        获得窗体对象用于获得屏幕宽高
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        start_btn = (Button) findViewById(R.id.start_btn);
        alltime = (TextView) findViewById(R.id.all_time);
        count_time = (TextView) findViewById(R.id.count_time);
        di = (disu) findViewById(R.id.di);
//        获取一张图片对象
        bit = BitmapFactory.decodeResource(getResources(), R.mipmap.a2);

        activity = this;

        start_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn://点击开始之后发送消息给handle进行倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<=10;i++){
                            handler.sendEmptyMessage(1);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
        }
    }

    //在TimerTask的子类中处理需要倒计时的事件
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };



}
