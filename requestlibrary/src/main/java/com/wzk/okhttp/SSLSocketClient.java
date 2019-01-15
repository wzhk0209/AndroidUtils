package com.wzk.okhttp;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wangzhaokang on 2019/1/15.
 */

public class SSLSocketClient {

    public static SSLSocketFactory getSSLSocketFactory(){

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    private static TrustManager[] getTrustManager() {

        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        return trustManagers;
    }


    public static HostnameVerifier getHostnameVerifier(){
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        return hostnameVerifier;
    }
}
