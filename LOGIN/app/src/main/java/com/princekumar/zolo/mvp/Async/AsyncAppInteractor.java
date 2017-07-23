package com.princekumar.zolo.mvp.Async;

import android.content.Context;
import android.os.AsyncTask;

import com.princekumar.zolo.R;
import com.princekumar.zolo.data.UserAppDatabase;
import com.princekumar.zolo.data.entity.User;
import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;
import com.princekumar.zolo.uitls.EntryFieldValidation;
import com.princekumar.zolo.uitls.RandomPasswordGenerateor;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import timber.log.Timber;

import static com.princekumar.zolo.constant.ErrorCode.EMAIL_FAILURE_SEND;
import static com.princekumar.zolo.constant.ErrorCode.EMAIL_SUCCESSFUL_SEND;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_EMAIL_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_NAME_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PASSWORD_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PHONE_NUMBER_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_USER_ALREADY_EXIT;
import static com.princekumar.zolo.constant.ErrorCode.PROFILE_UPDATE;
import static com.princekumar.zolo.constant.ErrorCode.PROFILE_UPDATE_FAIL;
import static com.princekumar.zolo.constant.ErrorCode.USER_AVAILABLE;
import static com.princekumar.zolo.constant.ErrorCode.USER_NOT_AVAILABLE;
import static com.princekumar.zolo.constant.ErrorCode.USER_PASSWORD_INVALID;
import static com.princekumar.zolo.constant.ErrorCode.USER_PASSWORD_VALID;
import static com.princekumar.zolo.constant.ErrorCode.USER_REGISTRATION_SUCCESS;
import static com.princekumar.zolo.constant.ErrorCode.SUCCESS_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.VALID_USER_LOGIN_DATA;


public class AsyncAppInteractor {
    static private Context context;
    AppAllInterfaceTaskFinish.OnLoginFinishedListener clickListener;
    AppAllInterfaceTaskFinish.OnRegistrationFinishedListener onRegistrationFinishedListener;
    AppAllInterfaceTaskFinish.OnResetPasswordListener resetPasswordListener;
    AppAllInterfaceTaskFinish.OnProfileUpdateFinishedListener onProfileUpdateFinishedListener;

    private static String newPassword;

    public AsyncAppInteractor(Context context) {
        this.context = context;
    }

    public void validateCredentialsAsync(final AppAllInterfaceTaskFinish.OnLoginFinishedListener listener,
                                         final String phoneNumber, final String password) {
        this.clickListener = listener;
        LoginActivityAsync task = new LoginActivityAsync(UserAppDatabase.getAppDatabase(context), phoneNumber, password);
        task.execute();
    }

    public void validateRegistrationCredentialsAsync(final AppAllInterfaceTaskFinish.OnRegistrationFinishedListener listener,
                                                     final String phoneNumber, final String emailID, final String name,
                                                     final String password, final String referalCode) {
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

    public void resetPasswordAsync(final AppAllInterfaceTaskFinish.OnResetPasswordListener listener, final String emaiID) {
        this.resetPasswordListener = listener;
        PopulateDbResetPasswordAsync task = new PopulateDbResetPasswordAsync(UserAppDatabase.getAppDatabase(context), emaiID);
        task.execute();
    }

    public void updateProfileAsync(final AppAllInterfaceTaskFinish.OnProfileUpdateFinishedListener onProfileUpdateFinishedListener
            , final User user) {
        Timber.d(user.toString());
        this.onProfileUpdateFinishedListener = onProfileUpdateFinishedListener;
        PopulateUpdateProfileAsync task=new PopulateUpdateProfileAsync(UserAppDatabase.getAppDatabase(context), user);
        task.execute();

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

    private int profileDataValidation(User user) {
        EntryFieldValidation entryFieldValidation = new EntryFieldValidation();
        int errorCode = 0;
        if (!entryFieldValidation.phoneNumberValidate(user.getUserPhone())) {
            errorCode = ERROR_PHONE_NUMBER_VALIDATION;
        } else if (!entryFieldValidation.emailIDValidate(user.getUserEmailID())) {
            errorCode = ERROR_EMAIL_VALIDATION;
        } else if (!entryFieldValidation.nameValidate(user.getUserName())) {
            errorCode = ERROR_NAME_VALIDATION;
        } else {
            errorCode = SUCCESS_VALIDATION;
        }
        return errorCode;
    }


    private static int addUser(final UserAppDatabase db, User user) {
        Timber.d("Inside addUser " + (db.userDao().findByPhone(user.getUserPhone()) == null));
        Timber.d("Inside addUser " + db.userDao().findByPhone(user.getUserPhone()));
        if (db.userDao().findByPhone(user.getUserPhone()) == null) {
            db.userDao().insertAll(user);
        } else {
            Timber.d(getUserData(db, user.getUserPhone()).toString());
            return ERROR_USER_ALREADY_EXIT;
        }
        return USER_REGISTRATION_SUCCESS;

    }

    private static int checkUser(final UserAppDatabase db, String userPhoneNumber) {
        Timber.d("Inside checkUser " + (db.userDao().findByPhone(userPhoneNumber) == null));
        if (db.userDao().findByPhone(userPhoneNumber) == null) {
            return USER_NOT_AVAILABLE;
        } else {
            return USER_AVAILABLE;
        }
    }

    private static int checkUserByEmailID(final UserAppDatabase db, String emailID) {
        Timber.d("Inside checkUser " + (db.userDao().findByEmailID(emailID) == null));
        if (db.userDao().findByEmailID(emailID) == null) {
            return USER_NOT_AVAILABLE;
        } else {
            return USER_AVAILABLE;
        }
    }

    private static int checkUserByID(final UserAppDatabase db, int id) {
        Timber.d("Inside checkUser " + (db.userDao().findByID(id) == null));
        if (db.userDao().findByID(id) == null) {
            return USER_NOT_AVAILABLE;
        } else {
            return USER_AVAILABLE;
        }
    }


    private static User getUserDataByEmailID(final UserAppDatabase db, String emailID) {
        Timber.d("Inside getUserData " + db.userDao().findByEmailID(emailID).toString());
        return db.userDao().findByEmailID(emailID);
    }

    private static User getUserDataByID(final UserAppDatabase db, int id) {
        Timber.d("Inside getUserData " + db.userDao().findByID(id).toString());
        return db.userDao().findByID(id);
    }

    private static User getUserData(final UserAppDatabase db, String userPhoneNumber) {
        Timber.d("Inside getUserData " + db.userDao().findByPhone(userPhoneNumber).toString());
        return db.userDao().findByPhone(userPhoneNumber);
    }

    private static boolean passwordMatch(String loginPassword, String userPassword) {
        Timber.d("Inside passwordMatch " + (loginPassword.equals(userPassword)));
        return loginPassword.equals(userPassword);
    }

    private static boolean updateUserData(final UserAppDatabase db, User user) {
        db.userDao().updateUser(user);
        User newUserData = getUserDataByEmailID(db, user.getUserEmailID());
        return passwordMatch(newUserData.getUserPassword(), newPassword);
    }

    private static String newGeneratedPassword() {
        RandomPasswordGenerateor passwordGenerateor = new RandomPasswordGenerateor();
        // int randomPassword   =(int)(Math.random()*9000)+100000;
        String emailPassword = passwordGenerateor.generateRandomStringPassword();
        return emailPassword;
    }

    private static String emailContent() {
        newPassword = newGeneratedPassword();
        return "Your New Password: " + newPassword;
    }

    private class LoginActivityAsync extends AsyncTask<Void, Void, Integer> {

        private final UserAppDatabase mDb;
        private final String phoneNumber;
        private final String password;
        private User user;

        LoginActivityAsync(UserAppDatabase db, String phoneNumber, String password) {
            mDb = db;
            this.phoneNumber = phoneNumber;
            this.password = password;
            //Timber.d("Inside Pre PopulateDbAsync " + userData.toString());
        }

        @Override
        protected void onPreExecute() {
            Timber.d("Inside Pre Execute");
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(final Void... params) {
            int responseCode = checkUser(mDb, phoneNumber);
            Timber.d("Inside doInBackground" + responseCode);
            if (USER_AVAILABLE == responseCode) {
                User dbUserData = getUserData(mDb, phoneNumber);
                if (passwordMatch(password, dbUserData.getUserPassword())) {
                    responseCode = VALID_USER_LOGIN_DATA;
                    user = dbUserData;
                } else {
                    responseCode = USER_PASSWORD_INVALID;
                }

            }
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            Timber.d("Inside onPostExecute" + responseCode);
            if (responseCode == VALID_USER_LOGIN_DATA) {
                clickListener.onSuccess(user);
            } else {
                clickListener.onError(responseCode);
            }
            super.onPostExecute(responseCode);
        }
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


    private class PopulateDbResetPasswordAsync extends AsyncTask<Void, Void, Integer> {
        private Properties props;
        private final UserAppDatabase mDb;
        private final String emailID;

        PopulateDbResetPasswordAsync(UserAppDatabase db, String emailID) {
            mDb = db;
            this.emailID = emailID;
        }

        @Override
        protected void onPreExecute() {
            props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Timber.d("Inside Pre Execute");
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(final Void... params) {
            int responseCode = checkUserByEmailID(mDb, emailID);
            User user;
            final String senderEmailID = context.getResources().getString(R.string.sender_email_id).toString();
            final String senderPassword = context.getResources().getString(R.string.sender_email_password).toString();
            if (responseCode == USER_AVAILABLE) {
                user = getUserDataByEmailID(mDb, emailID);
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(senderEmailID, senderPassword);
                            }
                        });
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmailID));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(emailID));
                    message.setSubject("New Login Password");
                    message.setText(emailContent());

                    Transport.send(message);

                    System.out.println("Done");

                    user.setUserPassword(newPassword);
                    updateUserData(mDb, user);

                    responseCode = EMAIL_SUCCESSFUL_SEND;
                } catch (MessagingException e) {
                    Timber.e("MessagingException " + e.getMessage());
                    responseCode = EMAIL_FAILURE_SEND;
                } catch (Exception e) {
                    Timber.e(e.getMessage());
                    responseCode = EMAIL_FAILURE_SEND;
                }
            }
            Timber.d("Inside doInBackground" + responseCode);
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            Timber.d("Inside onPostExecute" + responseCode);
            if (responseCode == USER_PASSWORD_VALID) {
                resetPasswordListener.resetPasswordSuccess();
            } else {
                resetPasswordListener.resetPasswordError(responseCode);
            }
            super.onPostExecute(responseCode);
        }
    }

    private class PopulateUpdateProfileAsync extends AsyncTask<Void, Void, Integer> {
        private Properties props;
        private final UserAppDatabase mDb;
        private int id;
        private final User userData;
        User user;
        PopulateUpdateProfileAsync(UserAppDatabase db, User user) {
            mDb = db;
            this.userData = user;
        }

        @Override
        protected void onPreExecute() {
            id = userData.getUid();
            Timber.d("Inside Pre Execute" + userData.toString());
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(final Void... params) {

            int responseCode = profileDataValidation(userData);
            if (responseCode == SUCCESS_VALIDATION) {
                responseCode = checkUserByID(mDb, id);
                if (responseCode==USER_AVAILABLE){
                    user = getUserDataByID(mDb, id);
                    user.setUserEmailID(userData.getUserEmailID());
                    user.setUserPhone(userData.getUserPhone());
                    user.setUserName(userData.getUserName());
                    updateUserData(mDb, user);
                    user=getUserDataByID(mDb,user.getUid());
                    if (user.getUid()==userData.getUid()){
                        responseCode = PROFILE_UPDATE;
                    } else{
                        responseCode = PROFILE_UPDATE_FAIL;
                        Timber.d("Inside doInBackground" + responseCode);
                    }

                }
            }
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            Timber.d("Inside onPostExecute" + responseCode);
            if (responseCode == PROFILE_UPDATE) {
                onProfileUpdateFinishedListener.onProfileUpdateSuccess(user);

            } else {
                onProfileUpdateFinishedListener.onProfileUpdateError(responseCode);
            }
            super.onPostExecute(responseCode);
        }


    }
}
