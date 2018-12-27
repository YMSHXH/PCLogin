package com.example.king.pclogin.module;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pclogin.api.ApiUrl;
import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.contract.LoginContract;
import com.example.king.pclogin.net.RequestCallback;
import com.example.king.pclogin.util.OkHttpUtil;
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

    @Override
    public void setNetData(Map<String, String> params, final LoginContract.RequestCallback callback) {

        OkHttpUtil.getIntence().toPostLogin(params,ApiUrl.LOGIN_API,callback);

    }


}
