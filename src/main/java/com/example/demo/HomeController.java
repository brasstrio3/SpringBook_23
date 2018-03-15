package com.example.demo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {

        return "index";
    }

    @RequestMapping("/login")
public String login() {

        return "login";
    }

    @RequestMapping("/secure")
    public String secure(HttpServletRequest request, Authentication authentication, Principal principal) {
        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("User");
        /*UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = principal.getName();*/
        System.out.println(authentication.name());
        //authentication.name();
        return "secure";
    }


@Autowired
private UserService userService;

@RequestMapping(value="/register", method = RequestMethod.GET)
public String showRegistrationPage(Model model) {
    model.addAttribute("user", new User());
    return "registration";
}

@RequestMapping(value="/register", method = RequestMethod.POST)
public String processRegistrationPage (
        @Valid @ModelAttribute("user") User user,
        BindingResult result,
        Model model){
    model.addAttribute("user", user);
    if (result.hasErrors()) {
        return "registration";
    } else {
        userService.saveUser(user);
        model.addAttribute("message", "User Account Successfully Created");
}
return "index";
}
}


