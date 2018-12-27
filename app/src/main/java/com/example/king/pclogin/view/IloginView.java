package com.example.king.pclogin.view;

import com.example.king.pclogin.beans.UserLogin;

public interface IloginView {
    void mobileError(String msg);
    void pwdError(String msg);
    void failure(String msg);
    void success(UserLogin userLogin);
    void successMsg(String msg);
}
