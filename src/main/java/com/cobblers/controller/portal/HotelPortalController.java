package com.cobblers.controller.portal;


import com.cobblers.pojo.Hotel;
import com.cobblers.service.HotelService;
import com.cobblers.pojo.Order;
import com.cobblers.service.OrderService;
import com.cobblers.service.UserService;
import com.cobblers.utils.Tools;
import com.cobblers.base.BaseController;
import com.cobblers.base.BaseTemplates;
import com.cobblers.base.PageParam;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
/**
 * 酒店
 * <p>
 *  //TODO
 *  HotelPortalController.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/22 20:03
 * @see  HotelPortalController
 **/
@Controller
public class HotelPortalController extends BaseController {

    @Resource
    private HotelService hotelService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    @Resource
    private BaseTemplates<Hotel> baseTemplates;

    @RequestMapping("/hotelAccommodation")
    public ModelAndView hotelAccommodation(PageParam<?> pageParam){
        return baseTemplates.findList(pageParam,PORTAL_ROW,null,
                "portal/hotelAccommodation", hotelService.count(),
                ((currentPage, pageSize, query) -> hotelService.findByPage(currentPage,pageSize)));
    }

    @RequestMapping("/hotelPortalView")
    public ModelAndView hotelPortalView(String id){
        return baseTemplates.editInfo(id,"portal/hotelAccommodationView",i->hotelService.findById((String) i));
    }

    @RequestMapping("/goReserve")
    public ModelAndView goReserve(String id,HttpSession httpSession){
        var mv =BaseController.getModelAndView();
        try {
            mv.addObject("entity",hotelService.findById(id));
            mv.addObject("user",userService.findByUserName(httpSession.getAttribute("userName").toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("portal/reserve");
        return mv;
    }

    @RequestMapping("/hotelCreatOrder")
    public ModelAndView hotelCreatOrder(String hotelId, HttpServletRequest request,HttpSession httpSession){
        var mv =BaseController.getModelAndView();
        try {
            var hotel = hotelService.findById(hotelId);
            var user = userService.findByUserName(httpSession.getAttribute("userName").toString());
            var order  = new Order();
            this.bindValidateRequestEntity(request,order);
            order.setId(Tools.getUUID());
            order.setUserId(user.getId());
            if(Tools.isEmpty(order.getUserName())){
                order.setUserName(user.getUserName());
            }
            if(Tools.isEmpty(order.getLinkTel())){
                order.setLinkTel(user.getLinkTel());
            }
            if(Tools.isEmpty(order.getIcCode())){
                order.setIcCode(user.getIcCode());
            }
            order.setProductId(hotel.getId());
            order.setImgUrl(hotel.getImgUrl());
            order.setProductName(hotel.getHotelName());
            order.setProductType(2);

            order.setState(0);
            order.setOrderCode("O"+Tools.getUUID().substring(0,6).toUpperCase());
            order.setOrderTime(Tools.date2Str(new Date(),"yyyy-MM-dd"));
            orderService.save(order);
            mv.addObject("entity",hotel);
            mv.addObject("CreatSuccess",true);
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("portal/hotelAccommodationView");
        return mv;
    }
}
