package com.princekumar.zolo.mvp.Presenter;

import android.content.Context;

import com.princekumar.zolo.mvp.AppAllInterfacePresenter;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Async.AsyncAppInteractor;


public class RegistrationPresenter implements AppAllInterfacePresenter.IRegistrationPresenter
        ,AppAllInterfaceTaskFinish.OnRegistrationFinishedListener{

    private AppAllInterfaceView.IRegistrationView view;
    private AsyncAppInteractor interactor;
    private Context context;

    public RegistrationPresenter(AppAllInterfaceView.IRegistrationView registrationView, Context context) {
        this.view = registrationView;
        this.context=context;
        this.interactor = new AsyncAppInteractor(context);

    }


    @Override
    public void attemptRegistration(String phoneNumber, String emailID, String name, String password,String code) {

        interactor.validateRegistrationCredentialsAsync(this,phoneNumber,emailID,name,password,code);
    }

    @Override
    public void onRegError(int errorCode) {
        view.registrationFailed(errorCode);
    }

    @Override
    public void onRegSuccess() {
        view.navigateLoginActivity();
    }
}
