package com.nostalgia.shiroProject.controller;

import com.nostalgia.shiroProject.entity.Account;
import com.nostalgia.shiroProject.service.AccountService;
import com.nostalgia.shiroProject.util.CryptoUtil;
import com.nostalgia.shiroProject.util.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class MainConroller {
    @Autowired
    AccountService accountService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable String url){
        return url;
    }

    @PostMapping("/register")
    public String register(String username,String password,Model model){
        Account account=new Account();
        String salt= CryptoUtil.getRandomSalt();
        Md5Hash md5Hash=new Md5Hash(password,salt,1024);
        account.setUsername(username);
        account.setPassword(md5Hash.toHex());
        account.setSalt(salt);
        account.setRoles("user");
        if(accountService.save(account)!=true){
            model.addAttribute("msg","注册失败!");
            return "register";
        }
        else{
            model.addAttribute("msg","注册成功！");
            return "redirect:login";
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, String code,Model model,HttpSession session){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        String trueVerifyCode= (String) session.getAttribute("code");
        try {
            if(trueVerifyCode.equalsIgnoreCase(code)){
                subject.login(token);
                model.addAttribute("msg","登陆成功");
                return "redirect:index";
            }
            else{
                throw new RuntimeException("验证码错误");
            }
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名不存在");
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
        }catch (Exception e){
            if(e.getMessage().equals("验证码错误"))
                model.addAttribute("msg","验证码错误");
        }
        return "redirect:login";
    }

    @GetMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        //获取验证码
        String code= VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入session
        session.setAttribute("code",code);
        //将图片返回前端
        ServletOutputStream os=response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220,60,os,code);
    }

    //@RequiresRoles(value = {"user"})
    @GetMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        //System.out.println("into logout");
        return "redirect:login";
    }

    @GetMapping("/unauth")
    public String unauth(){
        return "unauth";
    }

}
