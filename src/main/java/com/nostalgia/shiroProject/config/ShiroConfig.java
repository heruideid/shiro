package com.nostalgia.shiroProject.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.nostalgia.shiroProject.cache.RedisCacheManager;
import com.nostalgia.shiroProject.shiro.filter.MyRolesAuthorizationFilter;
import com.nostalgia.shiroProject.shiro.realm.Md5Realm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getSecurityManager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);

        //自定义权限filter：对于指定url拥有一个role即可访问
        Map<String, Filter> filterMap=new HashMap<>();
        filterMap.put("roles",new MyRolesAuthorizationFilter());
        factoryBean.setFilters(filterMap);
        //配置角色权限等
        Map<String,String> map=new HashMap<>();
        //配置公有资源
        map.put("/login","anon");
        map.put("/register","anon");
        map.put("/getImage","anon");
        map.put("/index","anon");
        //配置受限资源
        map.put("/**","authc");
        map.put("/user","roles[user,admin]");
        map.put("/admin","roles[admin]");
        factoryBean.setFilterChainDefinitionMap(map);

        //设置默认登录页面
        factoryBean.setLoginUrl("/login");
        //设置未授权页面
        factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("md5Realm") Md5Realm md5Realm){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(md5Realm);
        return manager;
    }

    @Bean
    public Md5Realm md5Realm(){
        Md5Realm realm=new Md5Realm();
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher("md5");
        matcher.setHashIterations(1024);
        realm.setCredentialsMatcher(matcher);
        //开启缓存管理
        //realm.setCacheManager(new EhCacheManager());开启EhCache
        realm.setCacheManager(new RedisCacheManager());
        realm.setCachingEnabled(true);//开启全局缓存
        realm.setAuthenticationCachingEnabled(true);//开启认证缓存
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthorizationCachingEnabled(true);//开启授权认证
        realm.setAuthorizationCacheName("authorizationCache");
        return realm;
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
