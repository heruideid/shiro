package com.nostalgia.shiroProject;

import com.nostalgia.shiroProject.shiro.realm.Md5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class TestMd5 {
    public static void main(String[] args) {
        DefaultSecurityManager manager=new DefaultSecurityManager();
        Md5Realm realm=new Md5Realm();
        //给realm设置md5 hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);

        manager.setRealm(realm);

        SecurityUtils.setSecurityManager(manager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("henry","456");
        try {
            subject.login(token);
            System.out.println("登录成功");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名不存在");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }

        if(subject.isAuthenticated()){

            System.out.println(subject.hasRole("super"));
            System.out.println(subject.hasRole("admin"));

            //是否具有传入的所有角色
            System.out.println(subject.hasAllRoles(Arrays.asList("super", "admin")));

            //是否具有传入的集合中的角色，返回对应的boolean数组
            boolean[] booleans=subject.hasRoles(Arrays.asList("super", "admi"));
            for (int i = 0; i < booleans.length; i++) {
                System.out.println(booleans[i]);
            }

            //基于权限字符串的访问控制 资源标识符:操作:资源类型
            System.out.println("==========");
            System.out.println("权限："+subject.isPermitted("super:update:01"));


            System.out.println("我是分割线--------");
            //查看有哪些权限
            boolean[] permitted = subject.isPermitted("super:create:01", "super:update:02");
            for (boolean b : permitted) {
                System.out.println(b);
            }

            //查看是否具备所有权限
            System.out.println(subject.isPermittedAll("super:create:01", "super:update:02"));
        }


    }
}
