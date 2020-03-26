package com.rx.pub.role.controller;

import com.rx.pub.role.annotation.PermissionRole;
import com.rx.pub.role.enm.RolePermissionEumn;
import com.rx.pub.role.po.PubRoleOwnerPo;
import com.rx.pub.role.service.PubRoleOwnerService;
import com.rx.pub.role.vo.PubRoleOwnerAddVo;
import com.rx.pub.role.vo.PubRoleOwnerUpdateVo;
import com.rx.web.user.RxPermission;
import com.rx.pub.role.vo.PubRoleOwnerSearchVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.base.result.AlertTypeEnum;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.base.utils.StringUtil;

/**
 * 授权(PubRoleOwner)controller
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */
@RestController
@RequestMapping("/pubRoleOwner")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubRoleOwnerController")
public class PubRoleOwnerController {
    
    @Autowired
    private PubRoleOwnerService pubRoleOwnerService;


    @RequestMapping(value = "/addPubRoleOwner", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.授权)
    public DataResult addPubRoleOwner(@Validated PubRoleOwnerAddVo param,String[] roleIds){
    	
    	PubRoleOwnerPo search = new PubRoleOwnerPo();
    	
    	search.setOwnerId(param.getOwnerId());
    	search.setOwnerType(param.getOwnerType());
    	
    	List<PubRoleOwnerPo> olds = pubRoleOwnerService.select(search);
    	
        if(roleIds != null && roleIds.length > 0) {
        	List<PubRoleOwnerPo> recordList = new ArrayList<PubRoleOwnerPo>();
        	loop1:for(String id : roleIds) {
        		for(PubRoleOwnerPo rop : olds) {
        			if(id.equals(rop.getRoleId())) {
        				continue loop1;
        			}
        		}
        		PubRoleOwnerPo record = new PubRoleOwnerPo(null);
    	        BeanUtils.copyProperties(param, record);
    	        record.setRoleId(id);
    	        recordList.add(record);
        	}
        	if(recordList.size() > 0) {
        		pubRoleOwnerService.insertList(recordList);
        		return new DataResult("新加角色成功");
        	}else {
        		return new DataResult("所加角色已经拥有");
        	}
        }else {
        	boolean has = false;
        	for(PubRoleOwnerPo rop : olds) {
    			if(param.getRoleId().equals(rop.getRoleId())) {
    				has = true;
    				break;
    			}
    		}
        	if(has){
            	return new DataResult("所加角色已经拥有");
        	}else{
        		PubRoleOwnerPo record = new PubRoleOwnerPo(null);
	        	BeanUtils.copyProperties(param, record);
	        	pubRoleOwnerService.insertSelective(record);
	    		return new DataResult(record);
        	}
        }
    }
    

    @RequestMapping(value = "/updatePubRoleOwner", method = RequestMethod.POST)
    @ResponseBody
    @RxPermission
    public DataResult updatePubRoleOwner(@Validated PubRoleOwnerUpdateVo param){
        PubRoleOwnerPo old = pubRoleOwnerService.selectByPrimaryKey(param.getRoleOwnerRef());
    	if(old == null) {
    		throw new ValidateException("授权不存在");
    	}
    	PubRoleOwnerPo record = new PubRoleOwnerPo();
        BeanUtils.copyProperties(param, record);
        pubRoleOwnerService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    

    @RequestMapping(value = "/delPubRoleOwner", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.解除授权)
    public DataResult delPubRoleOwner(String[] id){
    	pubRoleOwnerService.deleteByPrimaryKeys(id);
    	//pubRoleOwnerService.deleteByPrimaryKey(id);
    	return new DataResult("删除角色成功");
    }
    

    @RequestMapping(value = "/listPubRoleOwnerPage", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.授权列表)
    public DataResult listPubRoleOwnerPage(@Validated PubRoleOwnerSearchVo param){
    	PubRoleOwnerPo po = new PubRoleOwnerPo();
        BeanUtils.copyProperties(param, po);
        return new DataResult(pubRoleOwnerService.searchPage(po));
    }
}