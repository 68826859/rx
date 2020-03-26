package com.rx.pub.role.dto;

import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.data.Model;
import com.rx.base.user.RxPermissionable;

/**
 */
@RxModel(text = "资源模型")
@ExtClass(extend = Model.class, alternateClassName = "PermissionEntityModel")
public class PermissionEntityModel implements RxPermissionable {
    private static final long serialVersionUID = 564814582033713219L;
    
    @RxModelField(text = "ID", isID = true)
    private String code;
    
    @RxModelField(text = "名称")
    private String name;
    
    @RxModelField(text = "分组")
    private String group;
    
    @RxModelField(text = "描述")
    private String desc;
    
    public PermissionEntityModel(RxPermissionable item) {
		this.setCode(item.getId());
		this.setName(item.getName());
		this.setGroup(item.getGroup());
		this.setDesc(item.getDesc());
	}
    
    
    public PermissionEntityModel() { }

    public String toJS(){
    	return "{name:'"+name+"',code:'"+code+"',group:'"+group+"',desc:'"+desc+"'}";
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public String getId() {
		return code;
	}

}