package com.example.king.pclogin.presenter;

import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.contract.LoginContract;
import com.example.king.pclogin.module.LoginModule;
import com.example.king.pclogin.net.RequestCallback;
import com.example.king.pclogin.util.ValidatorUtil;
import com.example.king.pclogin.view.IloginView;

import java.util.Map;

public class LoginPresenter {

    private LoginModule loginModule;
    private LoginContract.IloginView iloginView;

    public LoginPresenter(LoginContract.IloginView iloginView) {
        loginModule = new LoginModule();
        this.iloginView = iloginView;
    }

    public void login(Map<String,String> params) {
        //正则校验
        String mobile = params.get("mobile");
        if (!ValidatorUtil.isMobile(mobile)){
            if (iloginView!= null) {
                iloginView.mobileError("请输入合法手机号");
            }
            return;
        }

        //调用loginmodel的数据处理的方法，登录的方法
        if (loginModule!=null){
            loginModule.setNetData(params, new LoginContract.RequestCallback() {
                @Override
                public void failure(String msg) {
                    if (iloginView != null)
                        iloginView.failure(msg);
                }

                @Override
                public void successMsg(String msg) {
                    if (iloginView != null)
                        iloginView.successMsg(msg);
                }

                @Override
                public void success(UserLogin userLogin) {
                    if (iloginView != null)
                        iloginView.success(userLogin);
                }
            });
        }


    }
}
