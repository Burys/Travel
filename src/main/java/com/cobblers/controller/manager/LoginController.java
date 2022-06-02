package com.cobblers.controller.manager;

import com.cobblers.base.BaseController;
import com.cobblers.pojo.Admin;
import com.cobblers.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 管理员页面，登录判断，退出
 **/
@Controller
@Slf4j
public class LoginController extends BaseController {

    @Resource
    private AdminService adminService;

    //管理员登录页跳转
    @GetMapping("/login")
    public String login(HttpServletRequest request){
        Object user = request.getSession().getAttribute("admin");
        if (user != null) {
            return "redirect:/manager/index";
        }
        return "login";
    }

    //管理员登录判断
    @PostMapping("/loging")
    public String loging(String userName,String password,RedirectAttributes redirectAttributes,HttpServletRequest request){
        //管理员登录，判断用户名和密码是否为空
        if (Strings.isEmpty(userName)||Strings.isEmpty(password)){
            redirectAttributes.addFlashAttribute("message","用户名密码不得为空!");
            return "/login";
        }
        //管理员账号匹配
        try {
            Admin admin = adminService.login(userName, password);
            if (Objects.isNull(admin)){
                redirectAttributes.addFlashAttribute("message","用户名不存在或密码错误!");
                return "redirect:/login";
            }else{
                //判断管理员是否被禁用
                if (admin.getState() == 1) {
                    //管理员登录成功，存储session
                    request.getSession().setAttribute("admin", admin);
                    return "redirect:/manager/index";
                } else {
                    redirectAttributes.addFlashAttribute("message","账户已被停用!");
                    return "redirect:/login";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    //管理员退出
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        Object user = request.getSession().getAttribute("admin");
        if (user != null) {
            request.getSession().removeAttribute("admin");
        }
        return "/login";
    }
}
