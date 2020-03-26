package com.rx.pub.role.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.role.annotation.PermissionRole;
import com.rx.pub.role.enm.RolePermissionEumn;
import com.rx.pub.role.po.PubRolePo;
import com.rx.pub.role.service.PubRoleService;
import com.rx.pub.role.vo.PubRoleAddVo;
import com.rx.pub.role.vo.PubRoleSearchVo;
import com.rx.pub.role.vo.PubRoleUpdateVo;
import com.rx.web.user.RxPermission;
import com.rx.web.user.RxUser;

/**
 * (PubRole)controller
 *
 * @author klf
 * @since 2019-12-30 14:47:24
 */
@RestController
@RequestMapping("/pubRole")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubRoleController")
public class PubRoleController {
    
    @Autowired
    private PubRoleService pubRoleService;

    @PermissionRole(RolePermissionEumn.新增角色)
    @RequestMapping(value = "/addPubRole", method = RequestMethod.POST)
    @ResponseBody
    public DataResult addPubRole(@Validated PubRoleAddVo param){
        PubRolePo record = new PubRolePo(null);
        BeanUtils.copyProperties(param, record);
        RxUser<?> user = RxUser.getUser();
        record.setUserType(user.getClass().getName());
        record.setGroupId(user.getGroupId());
        pubRoleService.insertSelective(record);
    	return new DataResult(record);
    }
    

    @RequestMapping(value = "/updatePubRole", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.修改角色)
    public DataResult updatePubRole(@Validated PubRoleUpdateVo param){
        PubRolePo old = pubRoleService.selectByPrimaryKey(param.getRoleId());
    	if(old == null) {
    		throw new ValidateException("不存在");
    	}
    	PubRolePo record = new PubRolePo();
        BeanUtils.copyProperties(param, record);
        pubRoleService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    

    @RequestMapping(value = "/delPubRole", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.删除角色)
    public DataResult delPubRole(String id){
    	pubRoleService.deleteByPrimaryKey(id);
    	return new DataResult("删除角色成功");
    }
    
    @RequestMapping(value = "/listPubRole", method = RequestMethod.GET)
    @ResponseBody
    @RxPermission
    //@PermissionRole(RolePermissionEumn.角色列表)
    public DataResult listPubRole(@Validated PubRoleSearchVo param){
    	PubRolePo po = new PubRolePo();
        BeanUtils.copyProperties(param, po);
        RxUser<?> user = RxUser.getUser();
        po.setGroupId(user.getGroupId());
        po.setUserType(user.getClass().getName());
        return new DataResult(pubRoleService.select(po));
    }

    @RequestMapping(value = "/listPubRolePage", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.角色列表)
    public DataResult listPubRolePage(@Validated PubRoleSearchVo param){
    	PubRolePo po = new PubRolePo();
        BeanUtils.copyProperties(param, po);
        RxUser<?> user = RxUser.getUser();
        po.setGroupId(user.getGroupId());
        po.setUserType(user.getClass().getName());
        return new DataResult(pubRoleService.selectPage(po,null));
    }
}