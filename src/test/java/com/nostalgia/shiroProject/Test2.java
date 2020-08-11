package com.nostalgia.shiroProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        Integer[] array={4,2,1,3};
        List<Integer> list= new ArrayList<>(Arrays.asList(array));
        list.remove((int)Integer.valueOf(1));
        list.size();
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
