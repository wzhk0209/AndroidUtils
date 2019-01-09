package com.wzk.callback;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

/**
 * 实现此类可以写不通的解析
 */

public interface ICallBack {

    void onSuccess(String result);


    void onFailure(int status,String message);


}
