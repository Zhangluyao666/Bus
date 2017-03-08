package com.zxzq.bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBt_shearch;
    private EditText mEt_city;
    private EditText mEt_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEt_city = (EditText) findViewById(R.id.et_city);
        mEt_line = (EditText) findViewById(R.id.et_line);
        mBt_shearch = (Button) findViewById(R.id.bt_shearch);
        mBt_shearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEt_city.getText().toString().equals("")||mEt_line.getText().toString().equals("")){
                    Intent intent = new Intent(MainActivity.this,ThreeActivity.class);
                    startActivity(intent);
                    // Toast.makeText(MainActivity.this, "城市或线路不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("city",mEt_city.getText().toString());
                    bundle.putString("line",mEt_line.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
}
