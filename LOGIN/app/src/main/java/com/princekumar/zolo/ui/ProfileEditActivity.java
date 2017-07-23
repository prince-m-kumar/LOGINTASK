package com.princekumar.zolo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.princekumar.zolo.R;

import butterknife.BindView;

public class ProfileEditActivity extends AppCompatActivity {
    @BindView(R.id.et_login_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_login_password)
    EditText etLoginPassword;

    @BindView(R.id.bt_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
    }
}
