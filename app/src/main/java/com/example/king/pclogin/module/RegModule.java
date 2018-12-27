package com.example.king.pclogin.module;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pclogin.api.ApiUrl;
import com.example.king.pclogin.beans.UserReg;
import com.example.king.pclogin.contract.RegContract;
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

public class RegModule implements RegContract.IRegModule {

    Handler handler = new Handler();
    @Override
    public void setNetData(Map<String, String> params, final RegContract.RequestCallback callback) {

        OkHttpUtil.getIntence().toPostReg(params,ApiUrl.REG_API,callback);

    }

}
