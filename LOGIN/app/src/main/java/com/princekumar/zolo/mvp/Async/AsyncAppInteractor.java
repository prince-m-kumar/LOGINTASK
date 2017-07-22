package com.princekumar.zolo.mvp.Async;

import android.content.Context;

import com.princekumar.zolo.mvp.AppAllInterfaceTaskFinish;

import static com.princekumar.zolo.constant.ErrorCode.ERROR_PHONE_NUMBER_VALIDATION;

/**
 * Created by princ on 22-07-2017.
 */

public class AsyncAppInteractor {
    private Context context;
    AppAllInterfaceTaskFinish.OnLoginFinishedListener clickListener;

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
}
