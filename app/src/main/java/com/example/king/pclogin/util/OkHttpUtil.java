package com.example.king.pclogin.util;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pclogin.api.ApiUrl;
import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.beans.UserReg;
import com.example.king.pclogin.contract.LoginContract;
import com.example.king.pclogin.contract.RegContract;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    /**
     * 创建单例模式
     */
    private static OkHttpUtil intence;
    private final OkHttpClient okHttpClient;
    private final Handler handler;

    private OkHttpUtil() {
        handler = new Handler();
        //okhttp网络框架的管理类
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpUtil getIntence(){
        if (intence == null){
            synchronized (OkHttpUtil.class){
                if (intence == null){
                    intence = new OkHttpUtil();
                }
            }
        }
        return intence;
    }

    /**
     *  登录ok
     * @param params
     * @param api
     * @param callback
     */
    public void toPostLogin(Map<String,String> params, String api, final LoginContract.RequestCallback callback){
        //对请求体，构建数据的过程，建造者模式
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> p: params.entrySet() ) {
            formBody.add(p.getKey(),p.getValue());
        }

        //创建请求信息对象
        final Request request = new Request.Builder()
                .url(api)
                .post(formBody.build())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failure("网络可能不稳定，请稍后再试");
                        }
                    });
                }
            }

            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                int code = response.code();
                if (!TextUtils.isEmpty(result)){
                    paserResult(result,callback,code);
                }
            }
        });
    }

    /**
     * 注册ok
     * @param params
     * @param api
     * @param callback
     */
    public void toPostReg(Map<String,String> params, String api, final RegContract.RequestCallback callback){
        //对请求体，构建数据的过程，建造者模式
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> p: params.entrySet() ) {
            formBody.add(p.getKey(),p.getValue());
        }

        //创建请求信息对象
        final Request request = new Request.Builder()
                .url(api)
                .post(formBody.build())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failure("网络可能不稳定，请稍后再试");
                        }
                    });
                }
            }

            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                int code = response.code();
                if (!TextUtils.isEmpty(result)){
                    paserResult2(result,callback,code);
                }
            }
        });
    }

    /**
     *  注册
     * 解析数据变成对象
     * @param result
     * @param callback
     * @param code
     */
    private void paserResult2(String result, final RegContract.RequestCallback callback, int code) {
        final UserReg userReg = new Gson().fromJson(result, UserReg.class);

        if(callback != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.success(userReg);
                }
            });
        }

    }


    /**
     * 登录
     * 解析数据变成对象
     * @param result
     * @param callback
     * @param code
     */
    private void paserResult(String result, final LoginContract.RequestCallback callback, int code) {
        final UserLogin userLogin = new Gson().fromJson(result, UserLogin.class);

        if (callback != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.success(userLogin);
                }
            });
        }

    }

}
