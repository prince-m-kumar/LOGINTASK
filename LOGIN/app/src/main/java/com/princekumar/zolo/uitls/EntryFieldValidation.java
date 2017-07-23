package com.princekumar.zolo.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EntryFieldValidation {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{4,20})";
    private static final String EMAIL_PATTERN=
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String NAME_PATTERN=
            "[a-zA-Z][a-zA-Z ]*";
    public EntryFieldValidation(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean passwordValidate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public boolean phoneNumberValidate(final String phoneNumber){
        if (phoneNumber.length()!=10&&phoneNumber.startsWith("0")){
            return false;
        }
        return true;
    }

    public boolean emailIDValidate(final String email){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(EMAIL_PATTERN);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public boolean nameValidate(final String name){
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        return (name != null) && pattern.matcher(name).matches();
    }


}
