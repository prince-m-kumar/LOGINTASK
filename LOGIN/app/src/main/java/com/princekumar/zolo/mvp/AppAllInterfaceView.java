package com.princekumar.zolo.mvp;

/**
 * Created by princ on 22-07-2017.
 */

public class AppAllInterfaceView {

    public interface ILoginView {
        void navigateToProfileActivity();
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



}
