package com.princekumar.zolo.mvp;

import com.princekumar.zolo.data.entity.User;

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

    public interface OnProfileUpdateFinishedListener {
        void onProfileUpdateError(int errorCode);
        void onProfileUpdateSuccess(User user);
    }

}
