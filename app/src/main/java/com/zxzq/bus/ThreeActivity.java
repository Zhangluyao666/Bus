package com.zxzq.bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ThreeActivity extends AppCompatActivity {

    private ImageView mIv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
    }

    private void initView() {
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
