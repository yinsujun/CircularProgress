package com.progressapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mylibrary.CircleProgressView;

public class MainActivity extends AppCompatActivity {
    private CircleProgressView circleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_circle_progress_view);
        initView();
    }
    private void initView(){
        circleProgressView= (CircleProgressView) findViewById(R.id.progress_view);
        circleProgressView.setTopFloorText("小文字");
        circleProgressView.setTopText("上方文字");
        circleProgressView.setCenterText("中间文字");
        circleProgressView.setBottomText("下方文字");
        circleProgressView.setProgress(80);
        circleProgressView.setSpeed(40);
    }
}
