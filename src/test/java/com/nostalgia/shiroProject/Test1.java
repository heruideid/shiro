package com.nostalgia.shiroProject;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test1 {
    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("456");
        System.out.println(md5Hash.toHex());

        Md5Hash md5Hash1=new Md5Hash("456","x1.");
        System.out.println(md5Hash1.toHex());

        Md5Hash md5Hash2=new Md5Hash("456","x1.",1024);
        System.out.println(md5Hash2.toHex());
    }
}
