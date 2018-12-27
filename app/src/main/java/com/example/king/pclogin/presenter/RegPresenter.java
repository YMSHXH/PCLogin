package com.example.king.pclogin.presenter;

import com.example.king.pclogin.beans.UserReg;
import com.example.king.pclogin.contract.RegContract;
import com.example.king.pclogin.module.RegModule;
import com.example.king.pclogin.util.ValidatorUtil;

import java.util.Map;

public class RegPresenter {

    private RegModule regModule;
    private RegContract.IRegView iRegView;

    public RegPresenter(RegContract.IRegView iRegView) {
        this.regModule = new RegModule();
        this.iRegView = iRegView;
    }

    public void reg(Map<String,String> params) {

        //正则校验
        String mobile = params.get("mobile");
        if (!ValidatorUtil.isMobile(mobile)){
            if (iRegView!= null) {
                iRegView.mobileError("请输入合法手机号");
            }
            return;
        }

        if (regModule != null){
            regModule.setNetData(params, new RegContract.RequestCallback() {
                @Override
                public void failure(String msg) {
                    if (iRegView!= null) {
                        iRegView.failure(msg);
                    }
                }

                @Override
                public void successMsg(String msg) {
                    if (iRegView!= null) {
                        iRegView.successMsg(msg);
                    }
                }

                @Override
                public void success(UserReg userReg) {
                    if (iRegView!= null) {
                        iRegView.success(userReg);
                    }
                }
            });
        }
    }
}
