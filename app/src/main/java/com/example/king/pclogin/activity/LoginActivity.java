package com.example.king.pclogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.king.pclogin.R;
import com.example.king.pclogin.beans.UserLogin;
import com.example.king.pclogin.contract.LoginContract;
import com.example.king.pclogin.presenter.LoginPresenter;
import com.example.king.pclogin.view.IloginView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginContract.IloginView {

    private LoginPresenter loginPresenter;

    private EditText ed_url;
    private EditText ed_pwd;
    private Button btn_login;
    private Button btn_res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        ed_url = (EditText) findViewById(R.id.ed_url);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);

        //登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btn_res = (Button) findViewById(R.id.btn_zh);
        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    /**
     * 登录
     */
    private void Login() {
        String url = ed_url.getText().toString();
        String pwd = ed_pwd.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("mobile",url);
        params.put("password",pwd);

        if (loginPresenter !=null){
            loginPresenter.login(params);
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
    public void success(UserLogin userLogin) {
        Toast.makeText(this,userLogin.getMsg()+"",Toast.LENGTH_SHORT).show();
        if (userLogin.getMsg().equals("登录成功")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void successMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    /**
     * startActivityForResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == 2000){
            String url = data.getStringExtra("url");
            String pwd = data.getStringExtra("pwd");
            ed_url.setText(url);
            ed_pwd.setText(pwd);
        }
    }
}
