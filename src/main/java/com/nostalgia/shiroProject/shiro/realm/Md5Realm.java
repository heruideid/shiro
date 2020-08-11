package com.nostalgia.shiroProject.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nostalgia.shiroProject.entity.Account;
import com.nostalgia.shiroProject.shiro.salt.MyByteSource;
import com.nostalgia.shiroProject.service.AccountService;
import com.nostalgia.shiroProject.util.AccountUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class Md5Realm extends AuthorizingRealm {

    @Autowired
    AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",principalCollection.getPrimaryPrincipal());
        Account account=accountService.getOne(wrapper);
        List<String> roles= AccountUtil.getRoles(account.getRoles());
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        for (String role : roles) {
            info.addRole(role);
        }
        System.out.println("调用了授权认证-----");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal=(String)authenticationToken.getPrincipal();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",principal);
        Account account=accountService.getOne(wrapper);
        if(account==null) return null;
        else{
            return new SimpleAuthenticationInfo(principal,account.getPassword(), new MyByteSource(account.getSalt()),getName());
        }

//        if("henry".equals(principal)){
//            //1 只用md5加密
//            //return new SimpleAuthenticationInfo(principal,"250cf8b51c773f3f8dc8b4be867a9a02",getName());
//
//            //2 md5+salt
//            return new SimpleAuthenticationInfo(principal,"1ba60482d817815be94359591b246a9c", ByteSource.Util.bytes("x1."),getName());
//        }
//        return null;
    }
}
