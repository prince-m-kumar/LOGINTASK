package com.princekumar.zolo.mvp;

import com.princekumar.zolo.data.entity.User;

public class AppAllInterfaceView {

    public interface ILoginView {
        void navigateToProfileActivity(User user);
        void loginFailed(int errorCode);
    }

    public interface IRegistrationView {
        void navigateLoginActivity();
        void registrationFailed(int errorCode);

    }

    public interface IResetPasswordView {
        void navigateToLoginActivity();
        void resetPassword(int errorCode);
    }

    public interface IProfileUpdateView {
        void navigateProfileActivity(User user);
        void updateProfileFailed(int errorCode);

    }



}
