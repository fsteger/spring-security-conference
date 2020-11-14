package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Account;
import com.pluralsight.conference.service.AccountService;
import com.pluralsight.conference.util.OnCreateAccountEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("account")
    public String getRegistration(@ModelAttribute("account") Account account) {
        return "account";
    }

    @PostMapping("account")
    public String addRegistration(
            @Valid @ModelAttribute("account") Account account,
            BindingResult result
    ) {
        account.setPassword(encoder.encode(account.getPassword()));

        account = accountService.create(account);

        applicationEventPublisher.publishEvent(new OnCreateAccountEvent(account, "conference_war"));

        return "redirect:account";
    }

    @GetMapping("accountConfirm")
    public String confirmAccount(@RequestParam("token") String token) {
        accountService.confirmAccount(token);

        return "accountConfirmed";
    }
}
