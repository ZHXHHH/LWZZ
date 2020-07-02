package com.lwzz.forum.web.admin;

import com.lwzz.forum.po.User;
import com.lwzz.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;
    private HttpServletRequest request;


    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

/**
    @RequestMapping("/login")
    public String login(@RequestBody(required = false) Map<String, String> params,
                        HttpSession session,
                        RedirectAttributes attributes) {

        if (session.getAttribute("user") != null) return "admin/index";
        if (params == null) {
            System.out.println("username empty");
            return "redirect:/admin";
        } else {
            User user = userService.checkUser(params.get("username"), params.get("password"));
            if (user != null) {
                System.out.println(params.get("username"));
                user.setPassword(null);
                session.setAttribute("user", user);
                return "admin/index";
            } else {
                System.out.println("wrong pwd");
                attributes.addFlashAttribute("message", "用户名和密码错误");
                return "redirect:/admin";
            }
        }
    }
**/
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
