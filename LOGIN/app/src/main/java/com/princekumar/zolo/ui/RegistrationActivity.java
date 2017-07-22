package com.princekumar.zolo.ui;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.princekumar.zolo.BuildConfig;
import com.princekumar.zolo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.et_reg_phone_number)
    EditText etRegPhoneNumber;

    @BindView(R.id.et_reg_email)
    EditText etRegEmailID;

    @BindView(R.id.et_reg_name)
    EditText etRegName;

    @BindView(R.id.et_reg_password)
    EditText etRegPassword;

    @BindView(R.id.et_reg_referral_code)
    EditText etRegReferralCode;

    @BindView(R.id.btn_reg_register)
    Button btnRegister;

    @BindView(R.id.snackbarCoordinatorLayout)
    CoordinatorLayout snackbarCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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


    @OnClick(R.id.btn_reg_register)
    public void userRegistration(View view){
        Timber.d("btn_reg_register clicked");

    }

}
