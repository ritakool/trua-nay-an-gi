package com.example.ecommerce.controller;

import com.example.ecommerce.model.NguoiDung;
import com.example.ecommerce.service.NguoiDungService;
import com.example.ecommerce.service.SecurityService;
import com.example.ecommerce.validator.NguoiDungValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private NguoiDungValidator nguoiDungValidator;


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new NguoiDung());
        return "client/register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("newUser") @Valid NguoiDung nguoiDung, BindingResult bindingResult, Model model) {

        nguoiDungValidator.validate(nguoiDung, bindingResult);

        if (bindingResult.hasErrors()) {
            return "client/register";
        }

        nguoiDungService.saveUserForMember(nguoiDung);

        securityService.autologin(nguoiDung.getEmail(), nguoiDung.getConfirmPassword());

        return "redirect:/";
    }
}
