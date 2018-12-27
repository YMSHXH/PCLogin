package com.example.king.pclogin.contract;

import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.net.RequestCallback;

import java.util.Map;

public interface LoginContract {

    /**
     *  module层回调
     */
    interface IloginModule {
        void setNetData(Map<String, String> params, RequestCallback callback);
    }

    /**
     *  presenter接口回调
     */
    interface RequestCallback {
        void failure(String msg);//服务器请求失败：断网，服务器宕机等等因素
        void successMsg(String msg);//请求成功，但数据不正确
        void success(UserLogin userLogin);//数据合法
    }

    /**
     * view 层回调
     */
    interface IloginView {
        void mobileError(String msg);
        void pwdError(String msg);
        void failure(String msg);
        void success(UserLogin userLogin);
        void successMsg(String msg);
    }



}
