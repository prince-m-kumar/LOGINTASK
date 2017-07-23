package com.princekumar.zolo.uitls;

import java.util.Random;

/**
 * Created by princ on 22-07-2017.
 */

public class RandomPasswordGenerateor {
    private static final String CHAR_LIST_CAP =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHAR_LIST_SMALL ="abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_LIST_NUMBER="1234567890";
    private static final int RANDOM_STRING_LENGTH_CAP = 1;
    private static final int RANDOM_STRING_LENGTH_SMALL=3;
    private static final int RANDOM_STRING_LENGTH_NUMBER=2;


    private String generateRandomStringCap(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH_CAP; i++){
            int number = getRandomNumber(CHAR_LIST_CAP);
            char ch = CHAR_LIST_CAP.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private String generateRandomStringSmall(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH_SMALL; i++){
            int number = getRandomNumber(CHAR_LIST_SMALL);
            char ch = CHAR_LIST_SMALL.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private String generateRandomStringNumber(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH_NUMBER; i++){
            int number = getRandomNumber(CHAR_LIST_NUMBER);
            char ch = CHAR_LIST_NUMBER.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
    private int getRandomNumber(String passingData) {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(passingData.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }


    public String generateRandomStringPassword(){
        String password=generateRandomStringCap();
        password=password+generateRandomStringSmall();
        password=password+"@"+generateRandomStringNumber();


        return password;
    }


}
