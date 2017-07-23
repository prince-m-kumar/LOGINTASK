package com.princekumar.zolo.ui;

import android.app.ProgressDialog;
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
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Presenter.ResetPasswordPresenter;
import com.princekumar.zolo.uitls.EntryFieldValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.princekumar.zolo.R.id.snackbarCoordinatorLayout;

public class PasswordResetActivity extends AppCompatActivity implements AppAllInterfaceView.IResetPasswordView {

    @BindView(R.id.et_forgot_password_email)
    EditText etForgotPasswordEmailID;
    @BindView(R.id.btn_forgot_password)
    Button btnForgotPassword;
    @BindView(R.id.snackbarCoordinatorLayout)
    CoordinatorLayout snackbarCoordinatorLayout;

    ProgressDialog progressDialog;
    ResetPasswordPresenter passwordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        ButterKnife.bind(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        }
        etForgotPasswordEmailID.addTextChangedListener(mTextWatcher);
        checkFieldsForEmptyValues();
        passwordPresenter=new ResetPasswordPresenter(this,this);
    }

    @OnClick(R.id.btn_forgot_password)
    public void loginTapped(View view){
        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        String email =  etForgotPasswordEmailID.getText().toString();
        Timber.d(email);
        passwordPresenter.attemptResetPassword(email);


    }



    /*Password Screen Validation*/
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
        String s1 = etForgotPasswordEmailID.getText().toString();

        if(entryFieldValidation.emailIDValidate(s1)){
            btnForgotPassword.setEnabled(true);
            btnForgotPassword.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        } else {
            btnForgotPassword.setEnabled(false);
            btnForgotPassword.setBackgroundColor(getResources().getColor(R.color.colorYellowLight));
        }
    }

    @Override
    public void navigateToLoginActivity() {
        progressDialog.dismiss();
        Timber.d("Inside navigateToLoginActivity ");
        Intent myIntent = new Intent(PasswordResetActivity.this, LoginActivity.class);
        PasswordResetActivity.this.startActivity(myIntent);
    }

    @Override
    public void resetPassword(int errorCode) {
        progressDialog.dismiss();
        Timber.d("Inside resetPassword "+errorCode);
        notification(errorCode);
    }

    private void notification(int errorCode){
        ErrorMessage errorMessage=new ErrorMessage(PasswordResetActivity.this);
        String message=errorMessage.getErrorMessage(errorCode);
        Snackbar snackbar = Snackbar.make(
                snackbarCoordinatorLayout,
                message,
                Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(PasswordResetActivity.this, android.R.color.holo_red_dark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        snackbar.show();
    }

}
