package com.cobblers.controller.portal;

import com.cobblers.service.OrderService;
import com.cobblers.service.UserService;
import com.cobblers.base.BaseController;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <标题>
 * <p>
 *  //TODO
 *  OrderPortalController.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/22 22:41
 * @see  OrderPortalController
 **/
@Controller
public class OrderPortalController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    @RequestMapping("/myOrder")
    public ModelAndView myOrder(HttpSession httpSession,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize
    ) throws Exception {
        var mv = BaseController.getModelAndView();
        var user = userService.findByUserName(httpSession.getAttribute("userName").toString());
        var pageParam = orderService.findByPageByUserId(pageNum,pageSize,user.getId());
        mv.addObject("pageData", pageParam.getResult());
        mv.addObject("pageParam",pageParam);
        mv.setViewName("portal/myOrder");
        return mv;
    }

    @RequestMapping("/payOrder")
    public String payOrder(String id) throws Exception {
        var order = orderService.findById(id);
        order.setState(1);
        orderService.update(order);
        return REDIRECT+"/myOrder";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(String id) throws Exception {
        var order = orderService.findById(id);
        order.setState(2);
        orderService.update(order);
        return REDIRECT+"/myOrder";
    }
}
