package com.cobblers.base;

import com.cobblers.pojo.Order;
import com.cobblers.pojo.User;
import com.cobblers.service.OrderService;
import com.cobblers.service.UserService;
import com.cobblers.utils.Tools;
import com.cobblers.base.callback.ListCallBack;
import com.cobblers.base.callback.OptCallback;
import com.cobblers.base.callback.ProductObj;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 模板类
 **/
@Component
@Slf4j
public class BaseTemplates<T> {
    /**
     * 分页查询list(后台十页)
     *
     * @param pageParam 页码参数
     * @param row       一页的数据条数
     * @param query     搜索词
     * @param viewName  视图名
     * @param count     计数
     * @param callBack  list回调
     * @return {@link ModelAndView}
     */
    public ModelAndView findList(PageParam<?> pageParam, int row, String query, String viewName, long count, ListCallBack<T> callBack) {
        ModelAndView mv = BaseController.getModelAndView();
        pageParam.setCount(count);
        if(count<= pageParam.getPageSize()){
            pageParam.setSize(1);
        }else{
            //如果总数据是pageSize的倍速则取商否则取商加1
            pageParam.setSize(count%(pageParam.getPageSize())==0?count/(pageParam.getPageSize()):count/(pageParam.getPageSize())+1);
        }
        if(Tools.notEmpty(query)){
            List<T> userList = callBack.listCallBack(pageParam.getPageNumber(), pageParam.getPageSize(), query);
            mv.addObject("pageData", userList);
        }else{
            List<T> userList = callBack.listCallBack(pageParam.getPageNumber(), pageParam.getPageSize(), query);
            mv.addObject("pageData", userList);
        }
        mv.addObject("pageParam",pageParam);
        mv.addObject("query",query);
        mv.setViewName(viewName);
        return mv;

        // if (pageParam.getPageNumber() < 1) {
        //     pageParam = new PageParam<>();
        //     pageParam.setCount(count);
        //
        //     if (count <= row) {
        //         pageParam.setSize(1);
        //     } else {
        //         pageParam.setSize(count % row == 0 ? count / row : count / row + 1);
        //     }
        //     pageParam.setPageNumber(1);
        //     pageParam.setPageSize(row);
        // }
        // List<T> list = callBack.listCallBack(pageParam.getPageNumber(), pageParam.getPageSize(), query);
        // mv.addObject("pageData", list);
        // if (Tools.notEmpty(query)) {
        //     mv.addObject("query", query);
        //     pageParam.setCount(list.size());
        //     if (list.size() > pageParam.getPageSize()) {
        //         pageParam.setSize(list.size() / pageParam.getPageSize());
        //     } else {
        //         pageParam.setSize(1);
        //     }
        // }
        // mv.addObject("pageParam", pageParam);
        // mv.setViewName(viewName);
        // return mv;
    }



    /**
     * 对信息进行编辑
     *
     * @param id       信息所需id 无则传null
     * @param viewName 视图名
     * @param callback 返回的操作，一般为对象
     * @return ModelAndView
     */
    public ModelAndView editInfo(String id, String viewName, OptCallback<T> callback) {
        var mv = BaseController.getModelAndView();
        try {
            mv.addObject("entity", callback.doOpt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName(viewName);
        return mv;
    }

    /**
     * +
     * 保存或者更新操作模板
     *
     * @param request     请求对象
     * @param bc          当前对象
     * @param file        文件名
     * @param id          用户id
     * @param fn          保存的目录
     * @param tClass      对象反射
     * @param findObjBack 查找对象 （id存在时，无时创建对象）
     * @param editBack    编辑对象（更新或者操作，内部已经判断id是否存在）
     */
    public void updateOrSave(HttpServletRequest request,
                             BaseController bc,
                             MultipartFile file,
                             String id,
                             String fn,
                             Class<T> tClass,
                             OptCallback<T> findObjBack,
                             OptCallback<T> editBack) {

        T entity;
        try {
            if (Tools.notEmpty(id)) {
                entity = findObjBack.doOpt(id);
            } else {
                entity = tClass.getDeclaredConstructor().newInstance();
            }
            bc.bindValidateRequestEntity(request, entity);
            if (file != null && !file.isEmpty()) {
                var fileName = file.getOriginalFilename();
                int size = (int) file.getSize();
                log.info(fileName + "-->" + size);

                var path = "E:/idea/reviewProject/SpringBoot/travel/travel-stage/target/classes/static/" + fn;
                var dest = new File(path + "/" + fileName);
                //判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    //保存文件
                    file.transferTo(dest);
                    var setImgUrl = entity.getClass().getMethod("setImgUrl", String.class);
                    setImgUrl.invoke(entity, fn + "/" + fileName);
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            editBack.doOpt(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @param id id
     * @param viewName 视图名
     * @param httpSession session
     * @param getObj 获取单个对象
     * @param optOrder 额外设置订单
     * @param useReflex 使用反射，在有相同属性的情况下
     * @return
     */
    public ModelAndView creatOrder(String id,
                                   String viewName,
                                   HttpSession httpSession,
                                   OptCallback<T> getObj,
                                   ProductObj<T> optOrder,boolean useReflex) {
        var mv = BaseController.getModelAndView();
        try {
            var obj = getObj.doOpt(id);
            var user = userService.findByUserName(httpSession.getAttribute("userName").toString());
            var order = new Order();
            order.setId(Tools.getUUID());

            if (useReflex) {
                // obj
                try {
                    var imgUrl = (String) obj.getClass().getMethod("getImgUrl").invoke(obj);
                    order.setImgUrl(imgUrl);
                    var title = (String) obj.getClass().getMethod("getTitle").invoke(obj);
                    order.setProductName(title);
                    var price = (Double) obj.getClass().getMethod("getPrice").invoke(obj);
                    order.setFee(price);
                    var i = (String) obj.getClass().getMethod("getId").invoke(obj);
                    order.setProductId(i);
                } catch (NoSuchMethodException ex) {
                    log.error("{}视图中有方法不匹配，请检查", viewName);
                }
            }
            order.setOrderCode("O" + Tools.getUUID().substring(0, 6).toUpperCase());
            order.setRequirement("无");
            order.setState(0);
            order.setOrderTime(Tools.date2Str(new Date(), "yyyy-MM-dd"));

            // user
            order.setUserId(user.getId());
            order.setUserName(user.getUserName());
            order.setLinkTel(user.getLinkTel());
            order.setIcCode(user.getIcCode());

            // 订单额外操作
            order = (Order) optOrder.getObj(order,obj);
            // 保存订单
            orderService.save(order);
            mv.addObject("entity", obj);
            mv.addObject("CreatSuccess", true);
        } catch (Exception ex) {
            log.error("订单创建出现异常！");
            ex.printStackTrace();
        }
        mv.setViewName(viewName);
        return mv;
    }

}
