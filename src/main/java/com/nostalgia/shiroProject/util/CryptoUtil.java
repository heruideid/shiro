package com.nostalgia.shiroProject.util;

import java.util.Random;

public class CryptoUtil {
    public static String getRandomSalt(){
        StringBuffer s=new StringBuffer();
        Random random=new Random();
        int len=random.nextInt(3)+4;
        for(int i=0;i<len;i++){
            char ch=(char)('a'+random.nextInt(26));
            s.append(ch);
        }
        return s.toString();
    }
}
