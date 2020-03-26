package com.rx.pub.role.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rx.base.result.DataResult;
import com.rx.base.user.RxPermissionable;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.role.annotation.PermissionRole;
import com.rx.pub.role.dto.PermissionEntityModel;
import com.rx.pub.role.enm.RolePermissionEumn;
import com.rx.web.user.PermissionMgr;

/**
 * 资源(PubResource)controller
 *
 */
@RestController
@RequestMapping("/pubResource")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubResourceController")
public class PubResourceController {


	@RequestMapping(value = "/listAllResource", method = RequestMethod.GET)
	@ResponseBody
	@PermissionRole(RolePermissionEumn.角色的资源列表)
	public DataResult listAllResource() {
		List<PermissionEntityModel> list = new ArrayList<PermissionEntityModel>();
		for (RxPermissionable pe : PermissionMgr.getAllPermissionItems()) {
			PermissionEntityModel vo = new PermissionEntityModel(pe);
			list.add(vo);
		}
		return new DataResult(list);
	}
}