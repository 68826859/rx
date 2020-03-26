package com.rx.pub.role.controller;

import com.rx.pub.role.annotation.PermissionRole;
import com.rx.pub.role.enm.RolePermissionEumn;
import com.rx.pub.role.enm.RoleResourceReverseEumn;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.pub.role.service.PubRoleResourceService;
import com.rx.pub.role.service.RxRolePermissionService;
import com.rx.pub.role.vo.PubRoleResourceAddVo;
import com.rx.pub.role.vo.PubRoleResourceUpdateVo;
import com.rx.web.user.RxPermission;
import com.rx.pub.role.vo.PubRoleResourceSearchVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import java.util.Arrays;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.base.enm.EnumUtil;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.base.utils.StringUtil;

/**
 * 角色资源(PubRoleResource)controller
 *
 */
@RestController
@RequestMapping("/pubRoleResource")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubRoleResourceController")
public class PubRoleResourceController {
    
    @Autowired
    private PubRoleResourceService pubRoleResourceService;

    @Autowired
    private RxRolePermissionService rxRolePermissionService;
    
	@RequestMapping("/roleAddResource")
	@PermissionRole(RolePermissionEumn.新增角色的资源)
	public DataResult roleAddResource(@NotNull String roleId, @NotNull String[] resIds, int delOrAdd) throws Exception {
		pubRoleResourceService.addRoleResources(roleId, resIds, delOrAdd);
		return new DataResult("新增成功");
	}

	@RequestMapping("/roleDelResource")
	@PermissionRole(RolePermissionEumn.新增角色的资源)
	public DataResult roleDelResource(String roleId, @NotNull String[] refIds) throws Exception {
		pubRoleResourceService.deleteByPrimaryKeys(Arrays.asList(refIds));
		return new DataResult("删除成功");
	}
    
    
    @RequestMapping(value = "/addPubRoleResource", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.新增角色的资源)
    public DataResult addPubRoleResource(@Validated PubRoleResourceAddVo param){
        PubRoleResourcePo record = new PubRoleResourcePo();
        BeanUtils.copyProperties(param, record);
        record.setRoleResourceRef(StringUtil.getUUIDPure());
        pubRoleResourceService.insertSelective(record);
    	return new DataResult(record);
    }
    

    @RequestMapping(value = "/updatePubRoleResource", method = RequestMethod.POST)
    @ResponseBody
    @RxPermission
    public DataResult updatePubRoleResource(@Validated PubRoleResourceUpdateVo param){
        PubRoleResourcePo old = pubRoleResourceService.selectByPrimaryKey(param.getRoleResourceRef());
    	if(old == null) {
    		throw new ValidateException("角色资源不存在");
    	}
    	PubRoleResourcePo record = new PubRoleResourcePo();
        BeanUtils.copyProperties(param, record);
        pubRoleResourceService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    

    @RequestMapping(value = "/delPubRoleResource", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.删除角色的资源)
    public DataResult delPubRoleResource(String id){
    	pubRoleResourceService.deleteByPrimaryKey(id);
    	return new DataResult("成功");
    }
    
    @RequestMapping(value = "/listPubRoleResource", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.角色的资源列表)
    public DataResult listPubRoleResource(@Validated PubRoleResourceSearchVo param){
        String roleId = param.getRoleId();
        if(!StringUtils.hasText(roleId)){
        	throw new ValidateException("角色ID是必须的");
        }
        return new DataResult(rxRolePermissionService.getListRoleResourceDtoByRole(roleId,EnumUtil.valueOf(RoleResourceReverseEumn.class, param.getReverse(), null)));
    }
    

    @RequestMapping(value = "/listPubRoleResourcePage", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRole(RolePermissionEumn.角色的资源列表)
    public DataResult listPubRoleResourcePage(@Validated PubRoleResourceSearchVo param){
    	PubRoleResourcePo po = new PubRoleResourcePo();
        BeanUtils.copyProperties(param, po);
        return new DataResult(pubRoleResourceService.searchPage(po));
    }
}