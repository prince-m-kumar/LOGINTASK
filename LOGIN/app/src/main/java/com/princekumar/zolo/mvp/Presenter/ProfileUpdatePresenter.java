package com.princekumar.zolo.mvp.Presenter;

import android.content.Context;
import android.view.View;

import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.AppAllInterfacePresenter;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Async.AsyncAppInteractor;

/**
 * Created by princ on 23-07-2017.
 */

public class ProfileUpdatePresenter implements AppAllInterfacePresenter.IUpdateProfilePresenter, AppAllInterfaceTaskFinish.OnProfileUpdateFinishedListener {
    private AppAllInterfaceView.IProfileUpdateView view;
    private AsyncAppInteractor interactor;
    private Context context;


    public ProfileUpdatePresenter(AppAllInterfaceView.IProfileUpdateView view, Context context) {
        this.view = view;
        this.context = context;
        this.interactor = new AsyncAppInteractor(context);
    }

    @Override
    public void attemptProfileUpdate(User user) {
        interactor.updateProfileAsync(this, user);
    }

    @Override
    public void onProfileUpdateError(int errorCode) {
        view.updateProfileFailed(errorCode);
    }

    @Override
    public void onProfileUpdateSuccess(User user) {
        view.navigateProfileActivity(user);
    }
}
