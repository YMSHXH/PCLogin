package com.example.king.pclogin.module;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pclogin.api.ApiUrl;
import com.example.king.pclogin.beans.UserReg;
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

public class RegModule implements RegContract.IRegModule {

    Handler handler = new Handler();
    @Override
    public void setNetData(Map<String, String> params, final RegContract.RequestCallback callback) {

        //ok
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();

        //foreach
        FormBody.Builder formBoday = new FormBody.Builder();
        for (Map.Entry<String,String> p: params.entrySet()) {
            formBoday.add(p.getKey(),p.getValue());
        }

        //创建请求信息对象
        Request request = new Request.Builder()
                .url(ApiUrl.REG_API)
                .post(formBoday.build())
                .build();

        //创建请求执行对象
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failure("网络错误");
                    }
                });
            }

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

    private void paserResult(String result, final RegContract.RequestCallback callback, int code) {
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
}
