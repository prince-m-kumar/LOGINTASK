package com.princekumar.zolo.mvp;

/**
 * Created by princ on 22-07-2017.
 */

public class AppAllInterfaceView {

    public interface ILoginView {
        void navigateToProfileActivity();
        void loginFailed(int errorCode);
    }

}
