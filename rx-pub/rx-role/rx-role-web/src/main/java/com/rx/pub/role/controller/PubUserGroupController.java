package com.rx.pub.role.controller;

import com.rx.pub.role.po.PubUserGroupPo;
import com.rx.pub.role.service.PubUserGroupService;
import com.rx.pub.role.vo.PubUserGroupAddVo;
import com.rx.pub.role.vo.PubUserGroupUpdateVo;
import com.rx.web.user.RxPermission;
import com.rx.pub.role.vo.PubUserGroupSearchVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.base.utils.StringUtil;

/**
 * 用户组(PubUserGroup)controller
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */
@RestController
@RequestMapping("/pubUserGroup")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubUserGroupController")
public class PubUserGroupController {
    
    @Autowired
    private PubUserGroupService pubUserGroupService;


    @RequestMapping(value = "/addPubUserGroup", method = RequestMethod.POST)
    @ResponseBody
    @RxPermission
    public DataResult addPubUserGroup(@Validated PubUserGroupAddVo param){
        PubUserGroupPo record = new PubUserGroupPo();
        BeanUtils.copyProperties(param, record);
        record.setUserGroupRef(StringUtil.getUUIDPure());
        pubUserGroupService.insertSelective(record);
    	return new DataResult(record);
    }
    

    @RequestMapping(value = "/updatePubUserGroup", method = RequestMethod.POST)
    @ResponseBody
    @RxPermission
    public DataResult updatePubUserGroup(@Validated PubUserGroupUpdateVo param){
        PubUserGroupPo old = pubUserGroupService.selectByPrimaryKey(param.getUserGroupRef());
    	if(old == null) {
    		throw new ValidateException("用户组不存在");
    	}
    	PubUserGroupPo record = new PubUserGroupPo();
        BeanUtils.copyProperties(param, record);
        pubUserGroupService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    

    @RequestMapping(value = "/delPubUserGroup", method = RequestMethod.GET)
    @ResponseBody
    @RxPermission
    public DataResult delPubUserGroup(String id){
    	pubUserGroupService.deleteByPrimaryKey(id);
    	return new DataResult("成功");
    }
    

    @RequestMapping(value = "/listPubUserGroupPage", method = RequestMethod.GET)
    @ResponseBody
    @RxPermission
    public DataResult listPubUserGroupPage(@Validated PubUserGroupSearchVo param){
    	PubUserGroupPo po = new PubUserGroupPo();
        BeanUtils.copyProperties(param, po);
        return new DataResult(pubUserGroupService.searchPage(po));
    }
}