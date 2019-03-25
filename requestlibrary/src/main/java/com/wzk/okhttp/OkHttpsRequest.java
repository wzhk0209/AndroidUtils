package com.wzk.okhttp;

import com.wzk.callback.ICallBack;
import com.wzk.request.Request;
import com.wzk.request.RequestConstact;
import com.wzk.request.RequestType;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangzhaokang on 2019/1/8.
 */


/**
 * https请求
 */
public class OkHttpsRequest  implements Request{

    private OkHttpClient mOkHttpClient;

    private static volatile OkHttpsRequest _instance;

    public static OkHttpsRequest getInstance(){

        if(_instance == null){
            _instance = new OkHttpsRequest();
        }
        return _instance;
    }

    private OkHttpsRequest(){
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(RequestConstact.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(RequestConstact.TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }


    @Override
    public void sendRequest(String url, Map<String, Object> params, RequestType type, final ICallBack iCallBack) {

        okhttp3.Request request = null;
        switch (type){
            case POST:
                FormBody.Builder builder = new FormBody.Builder();

                if (params != null && params.size() > 0) {
                    Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        builder.add(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
                RequestBody requestBody = builder.build();
                 request = new okhttp3.Request.Builder()
                        .post(requestBody)
                        .url(url)
//                .addHeader()
                        .build();
                break;

            case GET:
                String finalUrl;
                StringBuilder builderParams = new StringBuilder();
                builderParams.append(url);

                if (params != null && params.size() > 0) {

                    builderParams.append("?");
                    Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        builderParams.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

                    }
                    builderParams.deleteCharAt(builderParams.lastIndexOf("&"));
                }

                finalUrl = builderParams.toString();
                request = new okhttp3.Request.Builder()
                        .get()
                        .url(finalUrl)
//                .addHeader() // 如果需要Header信息的话，请加入
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
