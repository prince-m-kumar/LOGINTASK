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

import com.princekumar.zolo.R;
import com.princekumar.zolo.constant.ErrorMessage;
import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Presenter.ProfileUpdatePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ProfileEditActivity extends AppCompatActivity implements AppAllInterfaceView.IProfileUpdateView {
    @BindView(R.id.et_profile_edit_email)
    EditText etProfileEditEmail;

    @BindView(R.id.et_profile_edit_name)
    EditText etProfileEditName;

    @BindView(R.id.et_profile_edit_number)
    EditText etProfileEditNumber;

    @BindView(R.id.btn_profile_update)
    Button btnProfileUpdate;

    @BindView(R.id.snackbarCoordinatorLayout)
    CoordinatorLayout snackbarCoordinatorLayout;

    private User user;
    ProgressDialog progressDialog;
    ProfileUpdatePresenter updatePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            user= (User) intent.getSerializableExtra("USER");
            Timber.d(user.toString());
        }
        setValue();
        updatePresenter=new ProfileUpdatePresenter(this,this);
    }


    private void setValue(){
        Timber.d(user.getUserName() +" "+user.getUserEmailID() + " "+user.getUserPhone() );
        etProfileEditName.setText(user.getUserName());
        etProfileEditEmail.setText(user.getUserEmailID());
        etProfileEditNumber.setText(user.getUserPhone());

    }

    @OnClick(R.id.btn_profile_update)
    public void userRegistration(View view){
        Timber.d("btn_profile_update clicked");
        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        String phoneNumber =  etProfileEditNumber.getText().toString();
        String email = etProfileEditEmail.getText().toString();
        String name =  etProfileEditName.getText().toString();
        user.setUserName(name);
        user.setUserPhone(phoneNumber);
        user.setUserEmailID(email);
        Timber.d("onProfileUpdateSuccess New Data" +user.toString());
        updatePresenter.attemptProfileUpdate(user);
        // Pass user event straight to presenter

    }

    @Override
    public void navigateProfileActivity(User user) {
        progressDialog.dismiss();
        Intent myIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
        myIntent.putExtra("USER", user);
        ProfileEditActivity.this.startActivity(myIntent);
    }

    @Override
    public void updateProfileFailed(int errorCode) {
        progressDialog.dismiss();
        notification(errorCode);
    }

    private void notification(int errorCode){

        ErrorMessage errorMessage=new ErrorMessage(ProfileEditActivity.this);
        String message=errorMessage.getErrorMessage(errorCode);
        Snackbar snackbar = Snackbar.make(
                snackbarCoordinatorLayout,
                message,
                Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(ProfileEditActivity.this, android.R.color.holo_red_dark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        snackbar.show();
    }
}
