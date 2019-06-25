package com.webanwendung.controller;

import com.webanwendung.Entitys.User;
import com.webanwendung.Service.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class loginController {
    public final String LOGINPAGE = "loginPage";
    public final String REGISTATIONPAGE = "RegistationPage";
    private final String USER = "user";
    @Autowired
    UserService userService;
    @Autowired 
    PasswordEncoder passwordencoder;
    /**
     *  method to guarantee that User is available in Model
     * @param m
     */
    @ModelAttribute(USER)
    public void initUser(Model m) {
        m.addAttribute(USER, new User());
    }
    @GetMapping("/login")
    public String getLogin(){
        return LOGINPAGE;
    }

    @GetMapping("/logout")
    public String getLogout(){
        return LOGINPAGE;
    }

    @GetMapping("/registation")
    public String getRegistration(){
        return REGISTATIONPAGE;
    }
    @PostMapping("/registation")
    public String Registration(Model m,@ModelAttribute User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        userService.addUser(user);
        return LOGINPAGE;
    }

}