package com.wzk.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wzk.mvp.Presenter.BasePresenter;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends Activity {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresneter();
        mPresenter.attachView((V)this);
    }

    protected abstract T createPresneter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }
}
