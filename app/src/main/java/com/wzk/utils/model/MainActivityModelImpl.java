package com.wzk.utils.model;

import android.util.Log;

import com.wzk.RequestApplication;
import com.wzk.callback.RequestCallBack;
import com.wzk.utils.entity.NewTopResponseInfo;

import java.util.Map;

/**
 * Created by wangzhaokang on 2019/1/9.
 */

public class MainActivityModelImpl<T> implements MainActivityModel  {
//    @Override
//    public void getServiceData(String url, Map<String, Object> params,RequestCallBack<NewTopResponseInfo> callBack) {
//        RequestApplication.getInstance().sendGet(url, params, callBack);
//    }

    @Override
    public void getServiceData(String url, Map params, RequestCallBack callBack) {
        RequestApplication.getInstance().sendGet(url, params, callBack);
    }
}
