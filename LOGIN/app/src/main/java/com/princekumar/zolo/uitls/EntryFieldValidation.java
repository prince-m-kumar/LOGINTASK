package com.princekumar.zolo.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by princ on 22-07-2017.
 */

public class EntryFieldValidation {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String EMAIL_PATTERN=
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String NAME_PATTERN=
            "[a-zA-Z][a-zA-Z ]*";
    public EntryFieldValidation(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean passwordValidate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public boolean phoneNumberValidate(final String phoneNumber){
        if (phoneNumber.length()!=10||phoneNumber.startsWith("0")){
            return false;
        }
        return true;
    }



}
