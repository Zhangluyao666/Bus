package com.zxzq.bus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog dialog;
    String url;
    int i = 0;
    private TextView mTv_line;
    private TextView mTv_place;
    private TextView mTv_time;
    private ImageView mIv_return;
    private ImageView mIv_change;
    private RecyclerView mRe_rvs;
    private Bean.ResultBean mResultBean;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String city = getIntent().getStringExtra("city");
        String line = getIntent().getStringExtra("line");
        System.out.println("city:"+city+"line:"+line);
        mPath = "http://op.juhe.cn/189/bus/busline?key=6f0e2a5d983cd6045f11eb0086eb5b3c&dtype=json&city=" + city + "&bus=" +line;
        ;
        initData();
        initView();
    }

    private void initView() {
        mTv_line = (TextView) findViewById(R.id.tv_line);
        mTv_place = (TextView) findViewById(R.id.tv_place);
        mTv_time = (TextView) findViewById(R.id.tv_time);
        mIv_return = (ImageView) findViewById(R.id.iv_return);
        mIv_return.setOnClickListener( this);
        mIv_change = (ImageView) findViewById(R.id.iv_change);
        mIv_change.setOnClickListener( this);
        mRe_rvs = (RecyclerView) findViewById(R.id.recycle_view_second);
        mTv_time.setVisibility(View.INVISIBLE);
        mTv_place.setVisibility(View.INVISIBLE);
        mTv_line.setVisibility(View.INVISIBLE);
    }

    private void initData() {
        dialog = ProgressDialog.show(this, null, "加载中，请稍候...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json =  HttpClientUtil.HttpGet(mPath);
                Gson gson = new Gson();
                Bean bean = gson.fromJson(json, Bean.class);
                final List<Bean.ResultBean> result = bean.getResult();
                if (result != null) {
                    mResultBean = result.get(0);
                    final StringBuffer startTime = new StringBuffer(mResultBean.getStart_time());
                    startTime.insert(2, ':');
                    final StringBuffer endTime = new StringBuffer(mResultBean.getEnd_time());
                    endTime.insert(2, ':');
                    final List<Bean.ResultBean.StationdesBean> stationdes = mResultBean.getStationdes();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv_time.setVisibility(View.VISIBLE);
                            mTv_place.setVisibility(View.VISIBLE);
                            mTv_line.setVisibility(View.INVISIBLE);
                            mTv_line.setText(mResultBean.getKey_name()+"详情");
                            mTv_place.setText(mResultBean.getFront_name() + "-" + mResultBean.getTerminal_name());
                            mTv_time.setText("首 " + new StringBuffer(startTime.toString() + " 末 " + endTime.toString() + " 全程 " + mResultBean.getLength().split("\\.")[0] + " 公里"));
                            MyAdapter myAdapter = new MyAdapter(stationdes);
                            myAdapter.setOnItemClickListener(new MyAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int postion) {
                                    Toast.makeText(SearchActivity.this,stationdes.get(postion).getName(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            mRe_rvs.setAdapter(myAdapter);
                            mRe_rvs.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL, false));
                            dialog.dismiss();
                            mRe_rvs.setVisibility(View.VISIBLE);
                        }
                    });


                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(SearchActivity.this, "未搜索到结果，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();

                        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_return:
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_change:
                if(i%2==0) {
                    mTv_place.setText(mResultBean.getTerminal_name() + "-" + mResultBean.getFront_name());
                    mRe_rvs.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, true));
                    i++;
                }else {
                    mTv_place.setText(mResultBean.getFront_name() + "-" + mResultBean.getTerminal_name());
                    mRe_rvs.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                    i++;
                }
                break;

        }

    }
}
