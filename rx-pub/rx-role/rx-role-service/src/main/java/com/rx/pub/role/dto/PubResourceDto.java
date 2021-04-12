package com.rx.pub.role.dto;

import com.rx.base.model.annotation.RxModel;
import com.rx.base.user.RxPermissionable;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.role.po.PubResourcePo;


/**
 * 角色资源(PubResource)DTO
 *
 * @author klf
 * @date 2021-03-31 15:45:21
 */
@RxModel(text = "角色资源DTO")
@ExtClass(extend = Model.class, alternateClassName = "PubResourceDto")
public class PubResourceDto extends PubResourcePo implements RxPermissionable {
    private static final long serialVersionUID = 694960688577821167L;

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.getResourceId();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getResourceName();
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return this.getGroupName();
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return this.getDescp();
	}

}