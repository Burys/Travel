package com.cobblers.controller.portal;


import com.cobblers.pojo.Order;
import com.cobblers.pojo.ScenicSpot;
import com.cobblers.service.ScenicSpotService;
import com.cobblers.utils.Tools;
import com.cobblers.base.BaseController;
import com.cobblers.base.BaseTemplates;
import com.cobblers.base.PageParam;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <标题>
 * <p>
 *  //TODO
 *  ScenicSpotPortalController.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/22 22:42
 * @see  ScenicSpotPortalController
 **/
@Controller
@Slf4j
public class ScenicSpotPortalController extends BaseController {

    @Resource
    private ScenicSpotService scenicSpotService;

    @Resource
    private BaseTemplates<ScenicSpot> baseTemplates;

    @RequestMapping("/travelSpot")
    public ModelAndView travelSpot(PageParam<?> pageParam) throws Exception {
      return baseTemplates.findList(pageParam,PORTAL_ROW,null,"portal/travelSpot",scenicSpotService.count2(),
              ((currentPage, pageSize, query) -> {
                  try {
                      return scenicSpotService.findByPage(currentPage,pageSize);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  log.error("未查到portal/travelSpot的分页数据！");
                  return null;
              }));
    }

    @RequestMapping("/scenicSpotPortalView")
    public ModelAndView scenicSpotPortalView(String id){
        return baseTemplates.editInfo(id, "portal/travelSpotView", i -> scenicSpotService.findById((String) i));
    }

    @RequestMapping("/travelSpotCreatOrder")
    public ModelAndView scenicSpotPortalCreatOrder(String id, HttpSession httpSession){
      return baseTemplates.creatOrder(id,"portal/travelSpotView",httpSession,
              i -> scenicSpotService.findById((String) i),
              (obj,ss)->{
                  var o = (Order)obj;
                  o.setProductType(1);
                  o.setOrderCode("S"+ Tools.getUUID().substring(0,6).toUpperCase());
                  o.setSetoffTime(ss.getOpenTime());
                  o.setProductName(ss.getSpotName());
                  o.setFee(ss.getTicketsMessage());
                  o.setProductId(ss.getId());
                  o.setImgUrl(ss.getImgUrl());
                  return o;
              },false);
    }
}
