package com.example.king.pclogin.module;

import com.example.king.pclogin.net.RequestCallback;

import java.util.Map;

public interface IloginModule {

    void setNetData(Map<String, String> params, RequestCallback callback);
}
