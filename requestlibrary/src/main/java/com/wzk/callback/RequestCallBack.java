package com.wzk.callback;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

public abstract class RequestCallBack<Result> implements ICallBack {

    @Override
    public void onSuccess(String result) {

//        提供需要解析后再回调，供用户选在
        Class<?> clazz = analysusClassInfo(this);
        Result resultBean = (Result) JSON.parseObject(result, clazz);
        onSuccess(resultBean);
    }

    protected abstract void onSuccess(Result resultBean);

    private Class<?> analysusClassInfo(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) types[0];
    }

}
