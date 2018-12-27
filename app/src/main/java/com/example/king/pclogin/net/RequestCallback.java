package com.example.king.pclogin.net;

import com.example.king.pclogin.beans.UserLogin;

public interface RequestCallback {
    void failure(String msg);//服务器请求失败：断网，服务器宕机等等因素
    void successMsg(String msg);//请求成功，但数据不正确
    void success(UserLogin userLogin);//数据合法
}
