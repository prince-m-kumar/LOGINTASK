package com.princekumar.zolo.mvp.Presenter;

import android.content.Context;

import com.princekumar.zolo.mvp.AppAllInterfacePresenter;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.mvp.AppAllInterfaceView;
import com.princekumar.zolo.mvp.Async.AsyncAppInteractor;

/**
 * Created by princ on 23-07-2017.
 */

public class ResetPasswordPresenter  implements AppAllInterfacePresenter.IResetPasswordPresenter,
        AppAllInterfaceTaskFinish.OnResetPasswordListener {
    private AsyncAppInteractor interactor;
    private Context context;
    private AppAllInterfaceView.IResetPasswordView resetPasswordView;

    public ResetPasswordPresenter(AppAllInterfaceView.IResetPasswordView iResetPasswordView, Context context) {
        this.resetPasswordView = iResetPasswordView;
        this.context=context;
        this.interactor = new AsyncAppInteractor(context);
    }

    @Override
    public void resetPasswordError(int errorCode) {
        resetPasswordView.resetPassword(errorCode);

    }

    @Override
    public void resetPasswordSuccess() {
        resetPasswordView.navigateToLoginActivity();
    }

    @Override
    public void attemptResetPassword(String emailID) {
        interactor.resetPasswordAsync(this, emailID);
    }
}
