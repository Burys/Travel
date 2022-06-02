package com.cobblers.controller.manager;

import com.cobblers.pojo.TravelRoute;
import com.cobblers.service.TravelRouteService;
import com.cobblers.utils.Tools;
import com.cobblers.base.BaseController;
import com.cobblers.base.BaseTemplates;
import com.cobblers.base.PageParam;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <标题>
 * <p>
 * //TODO
 * xxx.java
 * </p>
 *
 * @author 佐斯特勒
 * @version v1.0.0
 * @date 2020/7/20 22:45
 * @see TravelRouteController
 **/
@Controller
@RequestMapping("/manager")
@Slf4j
public class TravelRouteController extends BaseController {

    @Resource
    private TravelRouteService travelRouteService;

    @Resource
    private BaseTemplates<TravelRoute> baseTemplates;

    /**
     * 根据信息查找路线列表
     * @param pageParam
     * @param query
     */
    @RequestMapping("/travelRouteList")
    public ModelAndView travelRouteList2(PageParam pageParam, @RequestParam(value = "query", required = false) String query) throws Exception {
        return baseTemplates.findList(pageParam, MANAGER_ROW, query,
                "travelRoute/travelRouteList",
                travelRouteService.count(query),
                (currentPage, pageSize, keyword) ->
                {
                    try {
                        return travelRouteService.findByPage(currentPage, pageSize, keyword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.error("'/travelRouteList' 接口异常，数据无法查询");
                    return null;
                });
    }

    @RequestMapping("/travelRouteAdd")
    public ModelAndView travelRouteAdd() {
        return baseTemplates.editInfo(null, "travelRoute/travelRouteEdit", i -> new TravelRoute());
    }

    @RequestMapping("/travelRouteView")
    public ModelAndView travelRouteView(String id) {
        return baseTemplates.editInfo(id,
                "travelRoute/travelRouteView",
                i -> {
                    try {
                        return travelRouteService.findById((String) i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.error("无法从id查询到对象！");
                    return null;
                });
    }

    @RequestMapping("/travelRouteEdit")
    public ModelAndView travelRouteEdit(String id) {
        return baseTemplates.editInfo(id,
                "travelRoute/travelRouteEdit",
                i -> {
                    try {
                        return travelRouteService.findById((String) i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.error("无法从id查询到对象！");
                    return null;
                });
    }

    @RequestMapping("/travelRouteSave")
    public String travelRouteSave(HttpServletRequest request, String id, @RequestParam("fileName") MultipartFile file) {
        baseTemplates.updateOrSave(request,
                this, file, id,
                "/travelRoute", TravelRoute.class,
                i -> {
                    try {
                        return travelRouteService.findById((String) i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    log.error("查询不到 TravelRout对象！");
                    return null;
                },
                e -> {
                    var route = (TravelRoute) e;
                    try {
                        if (Tools.isEmpty(route.getId())) {
                            route.setId(Tools.getUUID());
                            travelRouteService.save(route);
                        } else {
                            travelRouteService.update(route);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return null;
                });
        return REDIRECT + "/manager/travelRouteList";
    }

    @RequestMapping("/travelRouteDelete")
    public String travelRouteDelete(String id) {
        if (Tools.notEmpty(id)) {
            try {
                travelRouteService.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return REDIRECT + "/manager/travelRouteList";
    }
}
