package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * @version 1.0
 * @user yzb
 * @date 2019-06-30 11:18
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JavaMailSender javaMailSender;
    @Reference
    private IUserService userService;
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/register")
    public String register(User user, HttpSession session){
        int random  = (int) session.getAttribute("random");
        System.out.println(random);
        System.out.println(user.getRandom());
        if (random==user.getRandom()){
            userService.register(user);
            return "toLogin";
        }
        return null;
    }

    @RequestMapping("/checkEmail")
    public void checkEmail(@RequestBody Email email,HttpSession session) throws Exception {
        System.out.println(email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper  messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("914673926yzb@sina.cn");
        messageHelper.setSubject("邮箱验证通知~~");
        /*产生一个四位数的随机数*/
        int max =9999;
        int min =1000;
        int random = (int)(Math.random()*(max-min)+min);
        System.out.println(random);
        /*将产生的随机数存入session中*/
        session.setAttribute("random",random);
        messageHelper.setText("您的验证码为"+random+"请严格保密，不要告诉他人");
        messageHelper.setSentDate(new Date());
        messageHelper.addTo(email.getEmailName());
        javaMailSender.send(mimeMessage);
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "toLogin";
    }

    @RequestMapping("/login")
    public String login(User user){
        List<User> users = userService.queryBy(user);
        System.out.println(user.getUsername()+"-->"+user.getPassword());
        System.out.println(users);
        for (User u : users) {
           if(u!=null){
                return "success";
            }else{
                return "toLogin";
            }
        }

       return null;
    }

    @RequestMapping("/forgetPassword/{username}")
    public void forgetPassword(@PathVariable String username,HttpSession session) throws MessagingException {
        System.out.println(username);
        List<User> users = userService.queryByName(username);
        for (User user : users) {
            if (user!=null){
                System.out.println(user);
               MimeMessage mimeMessage =javaMailSender.createMimeMessage();
               MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
               messageHelper.setFrom("914673926yzb@sina.cn");
               messageHelper.setSubject("重置密码提示");
               messageHelper.setText("请点击<a href=http://192.168.56.128:8080/user/rePassword?username="+user.getUsername()+">这里</a>重置密码",true);
               messageHelper.setSentDate(new Date());
               messageHelper.addTo(user.getEmail());
               javaMailSender.send(mimeMessage);
            }

        }
    }

    @RequestMapping("/rePassword")
    public String toRePassword(@RequestParam String username, Model model){
        List<User> users = userService.queryByName(username);

        for (User u:users){
           if(u!=null){
               model.addAttribute("user",u);
           }
        }
        return "rePassword";
    }

    @RequestMapping("/checkPassword")
    public String checkPassword(){

        return "toLogin";
    }
}
