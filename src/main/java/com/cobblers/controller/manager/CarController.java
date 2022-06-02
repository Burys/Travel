package com.cobblers.controller.manager;

import com.cobblers.pojo.Car;
import com.cobblers.service.CarService;
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
 * StrategyController.java
 * </p>
 *
 * @author 佐斯特勒
 * @version v1.0.0
 * @date 2020/7/22 16:20
 * @see CarController
 **/
@Slf4j
@RequestMapping("/manager")
@Controller
public class CarController extends BaseController {
    @Resource
    private CarService carService;

    @Resource
    private BaseTemplates<Car> baseTemplates;

    @RequestMapping("/carList")
    public ModelAndView carList(PageParam pageParam, @RequestParam(value = "query", required = false) String query) throws Exception {
        return baseTemplates.findList(pageParam, MANAGER_ROW, query,
                "car/carList",
                carService.count(),
                (currentPage, pageSize, keyword) -> carService.findByPage(currentPage, pageSize, keyword));
    }

    @RequestMapping("/carAdd")
    public ModelAndView carAdd() {
        return baseTemplates.editInfo(null,
                "car/carEdit",
                o -> new Car());
    }

    @RequestMapping("/carView")
    public ModelAndView carView(String id) {
        return baseTemplates.editInfo(id,
                "car/carView",
                i -> carService.findById((String) i));

    }

    @RequestMapping("/carEdit")
    public ModelAndView carEdit(String id) {
        return baseTemplates.editInfo(id,
                "car/carEdit",
                i -> carService.findById((String) i));
    }

    @RequestMapping("/carSave")
    public String carSave(HttpServletRequest request, String id, @RequestParam("fileName") MultipartFile file) {
        baseTemplates.updateOrSave(request, this, file, id, "/car", Car.class,
                i -> carService.findById((String) i),
                e -> {
                    var car = (Car) e;
                    if (Tools.isEmpty(car.getId())) {
                        car.setId(Tools.getUUID());
                        carService.save(car);
                    } else {
                        carService.update(car);
                    }
                    return null;
                });
        return REDIRECT + "/manager/carList";
    }

    @RequestMapping("/carDelete")
    public String carDelete(String id) {
        System.out.println();
        if (Tools.notEmpty(id)) {
            try {
                carService.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return REDIRECT + "/manager/carList";
    }


}
