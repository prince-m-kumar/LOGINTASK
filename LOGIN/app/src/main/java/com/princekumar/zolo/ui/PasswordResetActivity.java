package com.princekumar.zolo.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.princekumar.zolo.BuildConfig;
import com.princekumar.zolo.R;
import com.princekumar.zolo.uitls.EntryFieldValidation;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PasswordResetActivity extends AppCompatActivity {

    @BindView(R.id.et_forgot_password_email)
    EditText etForgotPasswordEmailID;
    @BindView(R.id.btn_forgot_password)
    Button btnForgotPassword;


    ProgressDialog progressDialog;

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

}
