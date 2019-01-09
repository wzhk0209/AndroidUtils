package com.wzk.mvp.Presenter;

import java.lang.ref.WeakReference;

/**
 * Created by wangzhaokang on 2018/12/26.
 */

public class BasePresenter<T> implements Presenter{

    protected WeakReference<T> mViewRef;

    public void attachView(T view) {
        this.mViewRef = new WeakReference<T>(view);
    }

    public void dettachView() {
        this.mViewRef.clear();
    }

}
