package com.princekumar.zolo.mvp.Presenter;

import android.content.Context;

import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.AppAllInterfacePresenter;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Async.AsyncAppInteractor;

public class AppLoginPresenter implements AppAllInterfacePresenter.ILoginPresenter, AppAllInterfaceTaskFinish.OnLoginFinishedListener {
    private AppAllInterfaceView.ILoginView view;
    private AsyncAppInteractor interactor;
    private Context context;

    public AppLoginPresenter(AppAllInterfaceView.ILoginView loginView, Context context) {
        this.view = loginView;
        this.context = context;
        this.interactor = new AsyncAppInteractor(context);
    }


    @Override
    public void attemptLogin(String phoneNumber, String password) {
        interactor.validateCredentialsAsync(this, phoneNumber, password);
    }

    @Override
    public void onError(int errorCode) {
        view.loginFailed(errorCode);
    }

    @Override
    public void onSuccess(User user) {
        view.navigateToProfileActivity(user);
    }
}
