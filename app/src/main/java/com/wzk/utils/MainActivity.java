package com.wzk.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzk.RequestApplication;
import com.wzk.callback.RequestCallBack;
import com.wzk.utils.entity.NewTopResponseInfo;


public class MainActivity extends AppCompatActivity {

    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResult = findViewById(R.id.result);


    }

    public void testNet(View view) {

        String url = "http://v.juhe.cn/toutiao/index?type=top&key=cbf2fe6ac72e1f0a38974beb94fe912f";
        RequestApplication.getInstance().sendGet(url, null, new RequestCallBack<NewTopResponseInfo>() {
            @Override
            protected void onSuccess(NewTopResponseInfo resultBean) {
                Log.e("wzk","返回信息:"+resultBean.toString());
            }

            @Override
            public void onFailure(int status, String message) {
                Log.e("wzk",message);
            }
        });

    }


}
