package com.cobblers.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cobblers.pojo.Message;
import com.cobblers.mapper.MessageMapper;
import com.cobblers.service.MessageService;
import com.cobblers.common.template.CommonTemplate;
import com.cobblers.utils.Tools;
import com.cobblers.base.PageParam;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.MapperUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * <标题>
 * <p>
 *  //TODO
 *  MessageServiceImpl.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/22 15:26
 * @see  MessageServiceImpl
 **/
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private CommonTemplate<Message> commonTemplate;


    @Override
    public long count() {
//        return messageMapper.selectCountByExample(whereArgs("deleteStatus","0"));
        var example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0);
        return messageMapper.selectCountByExample(example);
    }

    @Override
    public long countByUserId(String userId) {
        return messageMapper.countByUserId(userId);
    }

    @Override
    public Message findById(String id) {
//        return messageMapper.selectOneByExample(whereArgs("deleteStatus","0","id",id));
        var example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0)
                .andEqualTo("id",id);
        return messageMapper.selectOneByExample(example);
    }

    @Override
    public List<Message> findList() {
//        var example = whereArgs("deleteStatus", "0");
        var example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0);
        example.orderBy("addTime").desc();
        return messageMapper.selectByExample(example);
    }

    @Override
    public void save(Message article) {
        article.setAddTime(new Date());
        messageMapper.insert(article);
    }

    @Override
    public void update(Message article) {
        article.setModifyTime(new Date());
//        var id = article.getId();
//        article.setId(null);
//        messageMapper.updateByExampleSelective(article,whereArgs("deleteStatus", "0","id",id));
        var example = new Example(Message.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0)
                .andEqualTo("id",article.getId());
        messageMapper.updateByExampleSelective(article,example);
    }

    @Override
    public void deleteByid(String id) {
        var message = new Message();
        message.setId(id);
        message.setDeleteStatus(1);
        messageMapper.updateByPrimaryKeySelective(message);
    }

    @Override
    public List<Message> findByPage(int currentPage, int pageSize, String query) {
        List<Message> list;
        PageHelper.startPage(currentPage, pageSize);
        if (Tools.notEmpty(query)) {
            list = messageMapper.findByQuery("%" + query + "%");
        } else {
            list = findList();
        }
        var pageInfo=new PageInfo<Message>(list);
        return pageInfo.getList();
    }

    @Override
    public PageParam<Message> findByPageByUserId(int currentPage, int pageSize, String userId) {
        return commonTemplate.findByPageByUserId(currentPage,
                pageSize,
                userId,
                ((c, p, uid) -> messageMapper.findListByUserId(uid)));
    }

    /**
     * where值设置
     * @param args 参数
     * @return Example
     *
     */
  /*  private Example whereArgs(String ...args){
        return MapperUtils.whereArgs(Message.class, args);
    }*/
}
