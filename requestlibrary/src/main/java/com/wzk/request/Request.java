package com.wzk.request;

import com.wzk.callback.ICallBack;

import java.util.Map;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

/**
 * 实现此接口可封装其他的请求，不用修改太多的代码
 */

public interface Request {


    /**
     * 提供get请求方式
     * @param url
     *          请求url
     * @param params
     *          请求参数
     * @param iCallBack
     *          请求回调
     */
    void get(String url, Map<String,Object> params,ICallBack iCallBack);

    /**
     * 提供get请求方式
     * @param url
     *          请求url
     * @param params
     *          请求参数
     * @param iCallBack
     *          请求回调
     */
    void post(String url, Map<String,Object> params,ICallBack iCallBack);

}
