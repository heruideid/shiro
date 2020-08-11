package com.nostalgia.shiroProject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class TestAuthentication {
    public static void main(String[] args) {
        //创建安全管理器
        DefaultSecurityManager securityManager=new DefaultSecurityManager();

        //给安全管理器设置Realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        //SecurityUtils 给全局安全工具类设置安全管理器对象
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前登录的对象 subject
        Subject subject = SecurityUtils.getSubject();

        //创建令牌
        UsernamePasswordToken token=new UsernamePasswordToken("henry","4596");

        System.out.println("认证状态: "+subject.isAuthenticated());
        try {
            subject.login(token);
            System.out.println("认证状态: "+subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在");
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }

        Realm realm;
        SimpleAuthenticationInfo info;
        Account account;
    }
}
