package com.princekumar.zolo.mvp;

import com.princekumar.zolo.data.entity.User;

/**
 * Created by princ on 22-07-2017.
 */

public class AppAllInterfaceTaskFinish {
    public interface OnLoginFinishedListener {
        void onError(int errorCode);
        void onSuccess(User user);
    }

    public interface OnRegistrationFinishedListener {
        void onRegError(int errorCode);
        void onRegSuccess();
    }

    public interface OnResetPasswordListener {
        void resetPasswordError(int errorCode);
        void resetPasswordSuccess();
    }


}
