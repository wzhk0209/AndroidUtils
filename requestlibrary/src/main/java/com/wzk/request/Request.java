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
     * @param type
     *          请求类型
     * @param iCallBack
     *          请求回调
     */
    void sendRequest(String url,Map<String,Object> params,RequestType type,ICallBack iCallBack);

}
