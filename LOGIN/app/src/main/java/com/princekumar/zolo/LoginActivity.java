package com.princekumar.zolo;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_login_password)
    EditText etLoginPassword;

    @BindView(R.id.bt_login)
    Button btnLogin;

    @BindView(R.id.btn_forget_password)
    Button btnForgetPassword;
    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize Butterknife
        ButterKnife.bind(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        }
    }


    @OnClick(R.id.bt_login)
    public void loginTapped(View view){
        Timber.d("bt_login clicked");

    }

    @OnClick(R.id.btn_forget_password)
    public void forgetPasswordTapped(View view){
        Timber.d("btn_forget_password clicked");

    }
    @OnClick(R.id.btn_create_account)
    public void createAccountTapped(View view){
        Timber.d("btn_create_account clicked");

    }
}
