package com.princekumar.zolo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.princekumar.zolo.BuildConfig;
import com.princekumar.zolo.R;
import com.princekumar.zolo.constant.ErrorMessage;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Presenter.RegistrationPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.princekumar.zolo.constant.ErrorCode.ERROR_PHONE_NUMBER_VALIDATION;

public class RegistrationActivity extends AppCompatActivity implements AppAllInterfaceView.IRegistrationView{
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

    RegistrationPresenter presenter;
    ProgressDialog progressDialog;
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
        presenter = new RegistrationPresenter(this,this);
    }


    @OnClick(R.id.btn_reg_register)
    public void userRegistration(View view){
        Timber.d("btn_reg_register clicked");
        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        String phoneNumber =  etRegPhoneNumber.getText().toString();
        String email = etRegEmailID.getText().toString();
        String name =  etRegName.getText().toString();
        String password = etRegPassword.getText().toString();
        String code=etRegReferralCode.getText().toString();
        // Pass user event straight to presenter
        presenter.attemptRegistration(phoneNumber, email,name,password,code);

    }

    @Override
    public void navigateLoginActivity() {
        progressDialog.dismiss();
        Intent myIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(myIntent);
    }

    @Override
    public void registrationFailed(int errorCode) {
        progressDialog.dismiss();
        notification(errorCode);
        Timber.d("Inside navigateToProfileActivity " +errorCode);

    }

    private void notification(int errorCode){
        ErrorMessage errorMessage=new ErrorMessage(RegistrationActivity.this);
        String message=errorMessage.getErrorMessage(errorCode);
        Snackbar snackbar = Snackbar.make(
                snackbarCoordinatorLayout,
                message,
                Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(RegistrationActivity.this, android.R.color.holo_red_dark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        snackbar.show();
    }



}
