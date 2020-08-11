package com.nostalgia.shiroProject.util;

import java.util.Arrays;
import java.util.List;

public class AccountUtil {
    public static List<String> getRoles(String roleStr){
        return Arrays.asList(roleStr.split("&"));
    }
}
