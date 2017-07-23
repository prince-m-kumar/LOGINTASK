package com.princekumar.zolo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.princekumar.zolo.R;
import com.princekumar.zolo.data.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;

    @BindView(R.id.tv_email_id)
    TextView tvProfileEmailID;


    @BindView(R.id.tv_profile_number)
    TextView tvProfileNumber;

    @BindView(R.id.tv_profile_about_me)
    TextView tvProfileAboutMe;

    @BindView(R.id.btn_profile_edit)
    Button btnProfileEdit;

    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            user= (User) intent.getSerializableExtra("USER");
            Timber.d(user.toString());
        }
        setValue();
    }

    private void setValue(){
        Timber.d(user.getUserName() +" "+user.getUserEmailID() + " "+user.getUserPhone() );
        tvProfileName.setText(user.getUserName());
        tvProfileEmailID.setText(user.getUserEmailID());
        tvProfileNumber.setText(user.getUserPhone());

    }

    @OnClick(R.id.btn_profile_edit)
    public void createAccountTapped(View view){
        Timber.d("btn_create_account clicked "+user.toString());
        Intent myIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
        myIntent.putExtra("USER", user);
        ProfileActivity.this.startActivity(myIntent);
    }


}
