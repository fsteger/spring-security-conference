package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AccountController {

    @GetMapping("account")
    public String getRegistration(@ModelAttribute("account") Account account) {
        return "account";
    }
}
