package com.wzk.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzk.RequestApplication;
import com.wzk.callback.RequestCallBack;
import com.wzk.mvp.BaseActivity;
import com.wzk.utils.entity.NewTopResponseInfo;
import com.wzk.utils.presenter.MainActivityPresenter;
import com.wzk.utils.view.MainActivityView;


public class MainActivity extends BaseActivity<MainActivityView, MainActivityPresenter<MainActivityView>> implements MainActivityView {

    private TextView mResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResult = findViewById(R.id.result);


    }

    @Override
    protected MainActivityPresenter<MainActivityView> createPresneter() {
        return new MainActivityPresenter<>();
    }


    public void testNet(View view) {

        String url = "http://v.juhe.cn/toutiao/index?type=top&key=cbf2fe6ac72e1f0a38974beb94fe912f";
        mPresenter.getServiceData(url,null);
        RequestApplication.getInstance().sendGet(url, null, new RequestCallBack<NewTopResponseInfo>() {
            @Override
            protected void onSuccess(NewTopResponseInfo resultBean) {
                Log.e("wzk", "返回信息:" + resultBean.toString());
            }

            @Override
            public void onFailure(int status, String message) {
                Log.e("wzk", message);
            }
        });

    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void onServiceDataSuccess(final NewTopResponseInfo responseInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResult.setText(responseInfo.toString());
            }
        });
    }

    @Override
    public void onServiceDataFailure(String message) {

    }
}
