package com.example.king.pclogin.module;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pclogin.api.ApiUrl;
import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.contract.LoginContract;
import com.example.king.pclogin.net.RequestCallback;
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

public class LoginModule implements LoginContract.IloginModule {

    Handler handler = new Handler();

    @Override
    public void setNetData(Map<String, String> params, final LoginContract.RequestCallback callback) {

        //okhttp网络框架的管理类
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();

        //对请求体，构建数据的过程，建造者模式
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> p: params.entrySet() ) {
            formBody.add(p.getKey(),p.getValue());
        }

        //创建请求信息对象
        final Request request = new Request.Builder()
                .url(ApiUrl.LOGIN_API)
//                .url("http://120.27.23.105/user/login?mobile=13563015479&password=1234567")
//                .get()
                .post(formBody.build())
                .build();

        //创建请求执行对象
        Call call = okHttpClient.newCall(request);

        //异步请求
        call.enqueue(new Callback() {
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
