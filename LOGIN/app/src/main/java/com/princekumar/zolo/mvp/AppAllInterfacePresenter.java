package com.princekumar.zolo.mvp;

/**
 * Created by princ on 22-07-2017.
 */

public class AppAllInterfacePresenter {
    public interface ILoginPresenter {
        void attemptLogin(String phoneNumber, String password);

    }

    public interface IRegistrationPresenter {
        void attemptRegistration(String phoneNumber, String emailID, String name, String passsword,String referalCode);

    }

}
