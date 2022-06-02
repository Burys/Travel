package com.cobblers.controller.manager;

import com.cobblers.service.ProvinceService;
import com.cobblers.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
/**
 * 管理员系统控制层
 **/
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private ProvinceService provinceService;

    //管理员登录成功重定向到主页
    @RequestMapping("/index")
    public ModelAndView manager() {
        ModelAndView mv = BaseController.getModelAndView();
        mv.setViewName("index");
        return mv;
    }

    //主页内联url
    @RequestMapping("/main")
    public ModelAndView main() throws Exception {
        ModelAndView mv = BaseController.getModelAndView();
        mv.addObject("porvice",provinceService.getProvinceCount());
        mv.setViewName("main");
        return mv;
    }

    //404页面
    @RequestMapping("/404")
    public ModelAndView notFound() {
        ModelAndView mv = BaseController.getModelAndView();
        mv.setViewName("404");
        return mv;
    }

    @RequestMapping("/systemParameter")
    public ModelAndView systemParameter() {
        ModelAndView mv = BaseController.getModelAndView();
        mv.setViewName("systemParameter/systemParameter");
        return mv;
    }

    @RequestMapping("/test")
    public void test(@RequestBody String resmsg) throws IOException {
        System.out.println(resmsg);
    }

}
