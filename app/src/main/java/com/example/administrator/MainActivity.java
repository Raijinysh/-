package com.example.administrator;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.io.*;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    ImageButton button;
    int[ ][ ] position = { {1,1,1,1},{1,1,1,1},{1,1,1,1},{1,1,1,1},{1,0,0,1}};
    int moveX;
    int moveY;
    int left;
    int right;
    int bottom;
    int top;
    int X;
    int Y;
    int step = 0;
    boolean init = false;
    private int lastX = 0;
    private int lastY = 0; //手指在屏幕上的坐标
    private boolean isDraged = false; //View是否被移动过
    private boolean isDrag = false; //判断是拖动还是点击
    private int mCount;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.textView);
        button = (ImageButton)findViewById(R.id.imageButton);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton2);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton3);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton4);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton5);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton6);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton7);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton8);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton9);
        button.setOnTouchListener(this);
        button = (ImageButton)findViewById(R.id.imageButton10);
        button.setOnTouchListener(this);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!init){
            ImageButton button1 = (ImageButton)findViewById(R.id.imageButton6);
            left = button1.getLeft();
            top = button1.getTop();
            ImageButton button2 = (ImageButton)findViewById(R.id.imageButton4);
            right = button2.getRight();
            bottom = button2.getBottom();
            moveY = (int)(bottom-top)/5;
            moveX = (int)(right-left)/4;
            float scale=getBaseContext().getResources().getDisplayMetrics().density;
            X = (int)(90*scale);
            Y = (int)(90*scale);
            init = true;
        }
        int parentRight = ((ViewGroup)v.getParent()).getWidth();
        int parentBottom = ((ViewGroup)v.getParent()).getHeight();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                isDraged = false;
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                int dx = (int) event.getRawX()-lastX;
                int dy = (int) event.getRawY()-lastY; //手指在屏幕上移动的距离
                if (isDraged){
                    isDrag = true; //如果已经被拖动过，那么无论本次移动的距离是否为零，都判定本次事件为拖动事件
                }else{
                    if (dx==0&&dy==0){
                        isDraged = false; //如果移动的距离为零，则认为控件没有被拖动过，灵敏度可以自己控制
                    }else{
                        isDraged = true;
                        isDrag = true;
                    }
                }
                int l = v.getLeft();
                int b = v.getBottom();
                int r = v.getRight();
                int t = v.getTop();
                int num[] = {0,0,0,0};//上下左右的定位数字
                while(t+10>top+num[0]*moveY){
                    num[0]++;
                }
                while(b-10>top+num[1]*moveY){
                    num[1]++;
                }
                while(l+10>left+num[2]*moveX){
                    num[2]++;
                }
                while(r-10>left+num[3]*moveX){
                    num[3]++;
                }
                System.out.println(" "+top+" "+bottom+" "+left+" "+right);
                System.out.println(" "+num[0]+" "+num[1]+" "+num[2]+" "+num[3]);
                if(dx<0&&num[2]-2>=0&&Math.abs(dx)>Math.abs(dy))
                {
                    if(position[num[0]-1][num[2]-2]==0&&position[num[1]-1][num[2]-2]==0){
                        l = l - X;
                        r = r - X;
                        position[num[0]-1][num[2]-2]=1;
                        position[num[1]-1][num[2]-2]=1;
                        position[num[0]-1][num[3]-1]=0;
                        position[num[1]-1][num[3]-1]=0;
                        step++;
                    }
                }
                else if(dx>0&&num[3]<=3&&Math.abs(dx)>Math.abs(dy))
                {
                    if(position[num[0]-1][num[3]]==0&&position[num[1]-1][num[3]]==0){
                        l = l + X;
                        r = r + X;
                        position[num[0]-1][num[3]]=1;
                        position[num[1]-1][num[3]]=1;
                        position[num[0]-1][num[2]-1]=0;
                        position[num[1]-1][num[2]-1]=0;
                        step++;
                    }
                }
                if(dy<0&&num[0]-2>=0&&Math.abs(dy)>Math.abs(dx))
                {
                    if(position[num[0]-2][num[2]-1]==0&&position[num[0]-2][num[3]-1]==0){
                        t = t - Y;
                        b = b - Y;
                        position[num[0]-2][num[2]-1]=1;
                        position[num[0]-2][num[3]-1]=1;
                        position[num[1]-1][num[2]-1]=0;
                        position[num[1]-1][num[3]-1]=0;
                        step++;
                    }
                }
                else if(dy>0&&num[1]<=4&&Math.abs(dy)>Math.abs(dx))
                {
                    if(position[num[1]][num[2]-1]==0&&position[num[1]][num[3]-1]==0){
                        t = t + Y;
                        b = b + Y;
                        position[num[1]][num[2]-1]=1;
                        position[num[1]][num[3]-1]=1;
                        position[num[0]-1][num[2]-1]=0;
                        position[num[0]-1][num[3]-1]=0;
                        step++;
                    }
                }
                v.layout(l, t, r, b);
                depend(num,v);
                if (mShowCount != null)
                    mShowCount.setText(Integer.toString(step));
                break;
        }
        return isDrag; //如果没有给view设置点击事件，需返回true，否则不会响应ACTION_MOVE,导致view不会被拖动
    }



    public void depend(int[] num,View v){
        int l = v.getLeft();
        int b = v.getBottom();
        int r = v.getRight();
        int t = v.getTop();
        if(num[0]!=num[1]&&num[2]!=num[3])
        {
            num[0]=num[1]=num[2]=num[3]=0;
        }
            while(t+10>top+num[0]*moveY){
                num[0]++;
            }
            while(b-10>top+num[1]*moveY){
                num[1]++;
            }
            while(l+10>left+num[2]*moveX){
                num[2]++;
            }
            while(r-10>left+num[3]*moveX){
                num[3]++;
            }
            if(num[0]==4&&num[1]==5&&num[2]==2&&num[3]==3)
            {
                Toast toast = Toast.makeText(this, "sucess", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
}
