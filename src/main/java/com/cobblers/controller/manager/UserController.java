package com.cobblers.controller.manager;

import com.cobblers.pojo.User;
import com.cobblers.service.UserService;
import com.cobblers.utils.Tools;
import com.cobblers.base.BaseController;
import com.cobblers.base.PageParam;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <标题>
 **/
@Controller
@RequestMapping("/manager")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    //用户列表查询
    @RequestMapping("/userList")
    public ModelAndView userList(PageParam<?> pageParam, @RequestParam(value = "query", required = false) String query) throws Exception {
        System.out.println("query:" + query);
        ModelAndView mv = BaseController.getModelAndView();
        long userCounts = 0;
        if(Tools.notEmpty(query)){
            userCounts = userService.countByQuery(query);
        }else{
            userCounts = userService.count();
        }
        pageParam.setCount(userCounts);
        if(userCounts<= pageParam.getPageSize()){
                pageParam.setSize(1);
        }else{
            //如果总数据是pageSize的倍速则取商否则取商加1
            pageParam.setSize(userCounts%(pageParam.getPageSize())==0?userCounts/(pageParam.getPageSize()):userCounts/(pageParam.getPageSize())+1);
        }
        if(Tools.notEmpty(query)){
            List<User> userList = userService.getUserByQuery(pageParam,query);
            mv.addObject("pageData", userList);
        }else{
            List<User> userList = userService.getUser(pageParam);
            mv.addObject("pageData", userList);
        }
        mv.addObject("pageParam",pageParam);
        mv.addObject("query",query);
        mv.setViewName("/user/allUsers");
        return mv;
    }

    //添加用户按钮接口
    @RequestMapping("/userAdd")
    public ModelAndView userAdd(){
        ModelAndView mv = BaseController.getModelAndView();
        mv.addObject("entity",new User());
        mv.setViewName("user/userEdit");
        return mv;
    }

    //查看用户基本信息
    @RequestMapping("/userView")
    public ModelAndView userView(String id){
        ModelAndView mv = BaseController.getModelAndView();
        try {
            mv.addObject("entity",userService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("user/userView");
        return mv;
    }

    //修改用户信息按钮接口
    @RequestMapping("/userEdit")
    public ModelAndView userEdit(String id){
        ModelAndView mv = BaseController.getModelAndView();
        try {
            mv.addObject("entity",userService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("user/userEdit");
        return mv;
    }

    //用户添加与修改
    @RequestMapping("/userSave")
    public ModelAndView userSave(HttpServletRequest request, String id, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView mv = BaseController.getModelAndView();
        User entity = null;
        try {
            if(Tools.notEmpty(id)){
                entity = userService.findById(id);
            }else{
                entity = new User();
            }
            this.bindValidateRequestEntity(request,entity);
            if (Tools.isEmpty(entity.getId())){
                User object = userService.findByUserName(entity.getUserName());
                if (object != null) {
                    mv.addObject("message","用户名已存在!");
                    mv.addObject("entity",entity);
                    mv.setViewName("user/userEdit");
                    return mv;
                }
                entity.setId(Tools.getUUID());
                userService.save(entity);
            }else{
                userService.update(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("pageData", userService.getUser(new PageParam<>()));
        var pageParam =new PageParam<>();
        long count = 0;
        try {
            count = userService.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pageParam.setCount(count);
        if(count<=10){
            pageParam.setSize(1);
        }else{
            pageParam.setSize(count%10==0?count/10:count/10+1);
        }
        pageParam.setPageNumber(1);
        pageParam.setPageSize(10);
        mv.addObject("pageParam",pageParam);
        mv.setViewName("user/allUsers");
        return mv;
    }

    //用户删除
    @RequestMapping("/userDelete")
    public String userDelete(String id){
        if(Tools.notEmpty(id)){
            try {
                userService.deleteByid(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/manager/userList";
    }
}
