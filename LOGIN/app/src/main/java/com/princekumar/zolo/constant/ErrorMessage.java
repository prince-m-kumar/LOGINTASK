package com.princekumar.zolo.constant;

import android.content.Context;

import com.princekumar.zolo.R;

import static com.princekumar.zolo.constant.ErrorCode.EMAIL_FAILURE_SEND;
import static com.princekumar.zolo.constant.ErrorCode.EMAIL_SUCCESSFUL_SEND;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_EMAIL_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_NAME_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PHONE_NUMBER_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_PASSWORD_VALIDATION;
import static com.princekumar.zolo.constant.ErrorCode.ERROR_USER_ALREADY_EXIT;
import static com.princekumar.zolo.constant.ErrorCode.PROFILE_UPDATE_FAIL;
import static com.princekumar.zolo.constant.ErrorCode.USER_AVAILABLE;
import static com.princekumar.zolo.constant.ErrorCode.USER_NOT_AVAILABLE;
import static com.princekumar.zolo.constant.ErrorCode.USER_PASSWORD_INVALID;
import static com.princekumar.zolo.constant.ErrorCode.USER_PASSWORD_VALID;
import static com.princekumar.zolo.constant.ErrorCode.USER_REGISTRATION_SUCCESS;


/**
 * Created by princ on 22-07-2017.
 */

public class ErrorMessage {

    Context context;

    public ErrorMessage(Context context) {
        this.context = context;
    }

    public String getErrorMessage(int errorCode) {
        String message = "UnKnownError Found";
        if (ERROR_PHONE_NUMBER_VALIDATION == errorCode)
            message = context.getResources().getString(R.string.msg_error_phone_validation);
        else if (ERROR_EMAIL_VALIDATION == errorCode)
            message = context.getResources().getString(R.string.msg_error_email_validation);
        else if (ERROR_NAME_VALIDATION == errorCode)
            message = context.getResources().getString(R.string.msg_error_name_validation);
        else if (ERROR_PASSWORD_VALIDATION == errorCode)
            message = context.getResources().getString(R.string.msg_error_password_validation);
        else if (ERROR_USER_ALREADY_EXIT == errorCode)
            message=context.getResources().getString(R.string.msg_error_user_already_exits);
        else if (USER_REGISTRATION_SUCCESS==errorCode)
            message=context.getResources().getString(R.string.msg_user_registration_success);
        else if (USER_NOT_AVAILABLE == errorCode)
            message = context.getResources().getString(R.string.msg_user_not_available);
        else if (USER_AVAILABLE == errorCode)
            message=context.getResources().getString(R.string.msg_user_available);
        else if (USER_PASSWORD_VALID==errorCode)
            message=context.getResources().getString(R.string.msg_user_password_valid);
        else if (USER_PASSWORD_INVALID == errorCode)
            message=context.getResources().getString(R.string.msg_user_password_invalid);
        else if (EMAIL_SUCCESSFUL_SEND==errorCode)
            message=context.getResources().getString(R.string.msg_email_send);
        else if (EMAIL_FAILURE_SEND==errorCode)
            message=context.getResources().getString(R.string.msg_email_fail);
        else if (PROFILE_UPDATE_FAIL==errorCode)
            message=context.getResources().getString(R.string.msg_profile_update_fail);

        return message;
}
}
