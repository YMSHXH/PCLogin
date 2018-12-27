package com.example.king.pclogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.king.pclogin.R;
import com.example.king.pclogin.beans.UserReg;
import com.example.king.pclogin.contract.RegContract;
import com.example.king.pclogin.presenter.RegPresenter;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity implements RegContract.IRegView {

    private RegPresenter regPresenter;

    private EditText ed_url;
    private EditText ed_pwd;
    private Button btn_reg;
    private String url;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();

        initData();

    }

    private void initData() {
        regPresenter = new RegPresenter(this);
    }

    private void initView() {
        ed_url = (EditText) findViewById(R.id.ed_url);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        btn_reg = (Button) findViewById(R.id.btn_login);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg();
            }
        });
    }

    private void Reg() {

        url = ed_url.getText().toString();
        pwd = ed_pwd.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", url);
        params.put("password", pwd);

        if (regPresenter != null){
            regPresenter.reg(params);
        }

    }


    @Override
    public void mobileError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pwdError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success(UserReg userReg) {
        if (userReg != null) {
            String msg = userReg.getMsg();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            if ("注册成功".equals(msg)){
                Intent intent = new Intent();
                intent.putExtra("url",url);
                intent.putExtra("pwd",pwd);
                setResult(2000,intent);
                finish();
            }
        }
    }

    @Override
    public void successMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
