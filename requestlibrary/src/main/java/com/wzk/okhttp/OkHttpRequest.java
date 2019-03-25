package com.wzk.okhttp;


import com.wzk.callback.ICallBack;
import com.wzk.request.Request;
import com.wzk.request.RequestConstact;
import com.wzk.request.RequestType;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangzhaokang on 2019/1/8.
 */

public class OkHttpRequest implements Request {

    private OkHttpClient mOkHttpClient;

    private static volatile OkHttpRequest _instance;

    public static OkHttpRequest getInstance() {
        if (_instance == null) {
            synchronized (OkHttpRequest.class) {
                if (_instance == null) {
                    _instance = new OkHttpRequest();
                }
            }
        }
        return _instance;
    }

    private OkHttpRequest() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(RequestConstact.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(RequestConstact.TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void sendRequest(String url, Map<String, Object> params, RequestType type,final ICallBack iCallBack) {
        // 判断是get还是post
        okhttp3.Request request = null;
        switch (type) {
            case GET:
                // 拼接url
                String finalUrl;
                StringBuilder builder = new StringBuilder();
                builder.append(url);

                if (params != null && params.size() > 0) {

                    builder.append("?");
                    Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

                    }
                    builder.deleteCharAt(builder.lastIndexOf("&"));
                }

                finalUrl = builder.toString();
                request = new okhttp3.Request.Builder()
                        .get()
                        .url(finalUrl)
//                .addHeader() // 如果需要Header信息的话，请加入
                        .build();
                break;

            case POST:
                // 组装参数
                FormBody.Builder paramBuilder = new FormBody.Builder();

                if (params != null && params.size() > 0) {
                    Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        paramBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
                RequestBody requestBody = paramBuilder.build();
                request = new okhttp3.Request.Builder()
                        .post(requestBody)
                        .url(url)
//                .addHeader()
                        .build();

                break;
        }
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iCallBack.onFailure(RequestConstact.REQUEST_SUCCESS, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    iCallBack.onSuccess(response.body().string());
                } else {
                    iCallBack.onFailure(RequestConstact.REQUEST_FAILURE, response.message());
                }
            }
        });

    }
}
