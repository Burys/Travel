package com.cobblers.controller.portal;


import com.cobblers.pojo.Car;
import com.cobblers.service.CarService;
import com.cobblers.pojo.Order;
import com.cobblers.service.OrderService;
import com.cobblers.service.UserService;
import com.cobblers.base.BaseController;
import com.cobblers.base.BaseTemplates;
import com.cobblers.base.PageParam;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 车票
 * <p>
 * //TODO
 * CarPortalController.java
 * </p>
 *
 * @author 佐斯特勒
 * @version v1.0.0
 * @date 2020/7/22 20:01
 * @see CarPortalController
 **/
@Controller
public class CarPortalController extends BaseController {
    @Resource
    private CarService carService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    @Resource
    private BaseTemplates<Car> baseTemplates;

    @RequestMapping("/car")
    public ModelAndView carList(PageParam<?> pageParam) {
        long count = 0;
        try {
            count = carService.state1count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseTemplates.findList(pageParam,PORTAL_ROW,null,"portal/car",count,
                ((currentPage, pageSize, query) -> carService.findByPage(currentPage,pageSize)));
    }

    @RequestMapping("/carPortalView")
    public ModelAndView carView(String id) {
        return baseTemplates.editInfo(id,"portal/carView",i->carService.findById((String) i));
    }

    @RequestMapping("/carCreatOrder")
    public ModelAndView carCreatOrder(String id, HttpSession httpSession) {
        return baseTemplates.creatOrder(id,"portal/carView",httpSession,
                i->carService.findById((String)i),
                (obj,car)->{
                    var o = (Order)obj;
                    o.setProductType(3);
                    o.setSetoffTime(car.getStartDateAndTime());
                    return o;
                },true);
    }
}
