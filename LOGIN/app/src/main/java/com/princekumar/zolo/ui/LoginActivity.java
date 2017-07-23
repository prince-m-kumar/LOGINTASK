package com.princekumar.zolo.ui;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.princekumar.zolo.BuildConfig;
import com.princekumar.zolo.R;
import com.princekumar.zolo.constant.ErrorMessage;
import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.Presenter.AppLoginPresenter;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.uitls.EntryFieldValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements AppAllInterfaceView.ILoginView{

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

    @BindView(R.id.snackbarCoordinatorLayout)
    CoordinatorLayout snackbarCoordinatorLayout;

    AppLoginPresenter appLoginPresenter;
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
        etPhoneNumber.addTextChangedListener(mTextWatcher);
        etLoginPassword.addTextChangedListener(mTextWatcher);
        checkFieldsForEmptyValues();
        appLoginPresenter=new AppLoginPresenter(this,this);
    }


    @OnClick(R.id.bt_login)
    public void loginTapped(View view){
        Timber.d("bt_login clicked");
        String email =  etPhoneNumber.getText().toString();
        String password = etLoginPassword.getText().toString();
        // Pass user event straight to presenter
        Timber.d(email + " "+ password);
        appLoginPresenter.attemptLogin(email, password);

    }

    @OnClick(R.id.btn_forget_password)
    public void forgetPasswordTapped(View view){
        Timber.d("btn_forget_password clicked");
        Intent myIntent = new Intent(LoginActivity.this, PasswordResetActivity.class);
        LoginActivity.this.startActivity(myIntent);

    }

    @OnClick(R.id.btn_create_account)
    public void createAccountTapped(View view){
        Timber.d("btn_create_account clicked");
        Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(myIntent);

    }

    /*Login Screen Validation*/
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        EntryFieldValidation entryFieldValidation=new EntryFieldValidation();
        String s1 = etPhoneNumber.getText().toString();
        String s2 = etLoginPassword.getText().toString();

        if(entryFieldValidation.passwordValidate(s2)&&entryFieldValidation.phoneNumberValidate(s1)){
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.colorYellowLight));
        }
    }

    @Override
    public void navigateToProfileActivity(User user) {
        Timber.d("navigateToProfileActivity mean Success"+user.toString());
        Intent myIntent = new Intent(LoginActivity.this, ProfileActivity.class);
        myIntent.putExtra("USER", user);
        LoginActivity.this.startActivity(myIntent);


    }

    @Override
    public void loginFailed(int errorCode) {
        Timber.d("loginFailed " +errorCode);
        notification(errorCode);
    }


    private void notification(int errorCode){
        ErrorMessage errorMessage=new ErrorMessage(LoginActivity.this);
        String message=errorMessage.getErrorMessage(errorCode);
        Snackbar snackbar = Snackbar.make(
                snackbarCoordinatorLayout,
                message,
                Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(LoginActivity.this, android.R.color.holo_red_dark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        snackbar.show();
    }

}
