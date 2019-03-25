package com.wzk;

import com.wzk.callback.RequestCallBack;
import com.wzk.okhttp.OkHttpRequest;
import com.wzk.okhttp.OkHttpsRequest;
import com.wzk.request.Request;
import com.wzk.request.RequestType;

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

    /**
     * 默认是http请求，想切换为https请求调用方法
     */
    public void setHttpsRequest(){
        this.mRequest = OkHttpsRequest.getInstance();
    }

    /**
     * 当请求是https的请求时，想要切换为http请求调用方法
     */
    public void setHttpRequest(){
        this.mRequest = OkHttpRequest.getInstance();
    }


    public void sendRequest(String url, Map<String, Object> params, RequestType type, RequestCallBack requestCallBack) {

        mRequest.sendRequest(url, params, type,requestCallBack);
    }

}
