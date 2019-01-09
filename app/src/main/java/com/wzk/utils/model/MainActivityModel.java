package com.wzk.utils.model;

import com.wzk.callback.RequestCallBack;

import java.util.Map;

/**
 * Created by wangzhaokang on 2019/1/9.
 */

public interface MainActivityModel<T> {

    void getServiceData(String url, Map<String,Object> params, RequestCallBack<T> callBack);
}
