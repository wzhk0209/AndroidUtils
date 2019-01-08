package com.wzk;

import com.wzk.callback.RequestCallBack;
import com.wzk.okhttp.OkHttpRequest;
import com.wzk.request.Request;

import java.util.Map;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

/**
 * 此类用于对外提供网络服务，可以修改构造方法中的实例对象来改变对应的网络请求
 */
public class RequestApplication {

    private Request mRequest;


    private RequestApplication() {
        /**
         * 可以在这里修改网络实例
         */
        mRequest =  OkHttpRequest.getInstance();
    }

    private static volatile RequestApplication _instance;

    public static RequestApplication getInstance() {

        if (_instance == null) {
            synchronized (RequestApplication.class) {
                if (_instance == null)
                    _instance = new RequestApplication();
            }
        }

        return _instance;
    }


    public void sendPost(String url, Map<String, Object> params, RequestCallBack requestCallBack) {

        mRequest.post(url, params, requestCallBack);
    }


    public void sendGet(String url, Map<String, Object> params, RequestCallBack requestCallBack) {

        mRequest.get(url, params, requestCallBack);
    }

}
