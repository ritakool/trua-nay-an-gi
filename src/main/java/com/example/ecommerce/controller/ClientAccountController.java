package com.example.ecommerce.controller;

import com.example.ecommerce.dto.PasswordDTO;
import com.example.ecommerce.model.DonHang;
import com.example.ecommerce.model.NguoiDung;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.service.DonHangService;
import com.example.ecommerce.service.NguoiDungService;
import com.example.ecommerce.service.SanPhamService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")

public class ClientAccountController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private DonHangService donHangService;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("loggedInUser")
    public NguoiDung loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return nguoiDungService.findByEmail(auth.getName());
    }

    public NguoiDung getSessionUser(HttpServletRequest request) {
        return (NguoiDung) request.getSession().getAttribute("loggedInUser");
    }

    @GetMapping("/account")
    public String accountPage(HttpServletRequest res, Model model) {
        NguoiDung currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        List<DonHang> list = Lists.reverse(donHangService.getDonHangByNguoiDung(currentUser));
        model.addAttribute("list", list);
        return "client/account";
    }

    @GetMapping("/changeInformation")
    public String clientChangeInformationPage(HttpServletRequest res, Model model) {
        NguoiDung currentUser = getSessionUser(res);
        model.addAttribute("user", currentUser);
        return "client/information";
    }

    @GetMapping("/changePassword")
    public String clientChangePasswordPage() {
        return "client/passwordChange";
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseObject commitChange(HttpServletRequest res, @RequestBody NguoiDung ng) {
        NguoiDung currentUser = getSessionUser(res);
        currentUser.setHoTen(ng.getHoTen());
        currentUser.setSoDienThoai(ng.getSoDienThoai());
        currentUser.setDiaChi(ng.getDiaChi());
        nguoiDungService.updateUser(currentUser);
        return new ResponseObject();
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseObject passwordChange(HttpServletRequest res, @RequestBody PasswordDTO dto) {
        NguoiDung currentUser = getSessionUser(res);
        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
            ResponseObject re = new ResponseObject();
            re.setStatus("old");
            return re;
        }
        nguoiDungService.changePass(currentUser, dto.getNewPassword());
        return new ResponseObject();
    }

}
