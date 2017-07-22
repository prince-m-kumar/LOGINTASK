package com.princekumar.zolo.mvp.Async;

import android.content.Context;
import android.os.AsyncTask;

import com.princekumar.zolo.data.UserAppDatabase;
import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.uitls.EntryFieldValidation;

import timber.log.Timber;

import static com.princekumar.zolo.constant.ErrorCode.ERROR_EMAIL_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_NAME_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PASSWORD_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PHONE_NUMBER_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_USER_ALREADY_EXIT;
import static com.princekumar.zolo.constant.ErrorCode.USER_REGISTRATION_SUCCESS;
import static com.princekumar.zolo.constant.ErrorCode.SUCCESS_VALIDATION;

/**
 * Created by princ on 22-07-2017.
 */

public class AsyncAppInteractor {
    private Context context;
    AppAllInterfaceTaskFinish.OnLoginFinishedListener clickListener;
    AppAllInterfaceTaskFinish.OnRegistrationFinishedListener onRegistrationFinishedListener;
    public AsyncAppInteractor(Context context) {
        this.context = context;
    }

    public void validateCredentialsAsync(final AppAllInterfaceTaskFinish.OnLoginFinishedListener listener, final String phoneNumber, final String password){
        this.clickListener = listener;
       if (phoneNumber.length()==10){
           clickListener.onSuccess();
       }else {
           clickListener.onError(ERROR_PHONE_NUMBER_VALIDATION);
       }
    }

    public void validateRegistrationCredentialsAsync(final AppAllInterfaceTaskFinish.OnRegistrationFinishedListener listener,
                                                     final String phoneNumber, final String emailID, final String name,
                                                     final String password,final String referalCode) {
        this.onRegistrationFinishedListener = listener;
        int errorCode = regValidation(phoneNumber, emailID, name, password);
        if (SUCCESS_VALIDATION == errorCode) {
            User user = new User();
            user.setUserPhone(phoneNumber);
            user.setUserEmailID(emailID);
            user.setUserName(name);
            user.setUserPassword(password);
            user.setReferalCode(referalCode);
            PopulateDbAsync task = new PopulateDbAsync(UserAppDatabase.getAppDatabase(context), user);
            task.execute();
        } else {
            onRegistrationFinishedListener.onRegError(errorCode);
        }
    }

    private int regValidation(String phoneNumber, String emailID, String name, String passsword) {
        EntryFieldValidation entryFieldValidation = new EntryFieldValidation();
        int errorCode = 0;
        if (!entryFieldValidation.phoneNumberValidate(phoneNumber)) {
            errorCode = ERROR_PHONE_NUMBER_VALIDATION;
        } else if (!entryFieldValidation.emailIDValidate(emailID)) {
            errorCode = ERROR_EMAIL_VALIDATION;
        } else if (!entryFieldValidation.nameValidate(name)) {
            errorCode = ERROR_NAME_VALIDATION;
        } else if (!entryFieldValidation.passwordValidate(passsword)) {
            errorCode = ERROR_PASSWORD_VALIDATION;
        } else {
            errorCode = SUCCESS_VALIDATION;
        }
        return errorCode;
    }


    private static int addUser(final UserAppDatabase db, User user) {
        Timber.d("Inside addUser " + (db.userDao().findByPhone(user.getUserPhone()) == null));
        if (db.userDao().findByPhone(user.getUserPhone()) == null) {
            db.userDao().insertAll(user);
        } else {
            return ERROR_USER_ALREADY_EXIT;
        }
        return USER_REGISTRATION_SUCCESS;

    }

    private class PopulateDbAsync extends AsyncTask<Void, Void, Integer> {

        private final UserAppDatabase mDb;
        private final User userData;

        PopulateDbAsync(UserAppDatabase db, User user) {
            mDb = db;
            this.userData = user;
            Timber.d("Inside Pre PopulateDbAsync " + userData.toString());
        }

        @Override
        protected void onPreExecute() {
            Timber.d("Inside Pre Execute");
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(final Void... params) {
            int responseCode = addUser(mDb, userData);
            Timber.d("Inside doInBackground" + responseCode);
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            Timber.d("Inside onPostExecute" + responseCode);
            if (responseCode == USER_REGISTRATION_SUCCESS) {
                onRegistrationFinishedListener.onRegSuccess();
            } else {
                onRegistrationFinishedListener.onRegError(responseCode);
            }
            super.onPostExecute(responseCode);
        }
    }

}
