package com.rx.pub.role.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.utils.StringUtil;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;

/**
 * 角色资源(PubResource)实体类
 *
 * @author klf
 * @date 2021-03-31 15:45:20
 */
@RxModel(text = "角色资源")
@Table(name = "pub_resource")
@ExtClass(extend = Model.class, alternateClassName = "PubResourcePo")
public class PubResourcePo implements Serializable {
	private static final long serialVersionUID = 192196940281204709L;

	@Id
	@RxModelField(text = "资源编号", isID = true)
	@Column(name = "resource_id")
	private String resourceId;

	@Column(name = "resource_name")
	@RxModelField(text = "资源名称")
	private String resourceName;

	@Column(name = "group_name")
	@RxModelField(text = "分组名称")
	private String groupName;

	@Column(name = "descp")
	@RxModelField(text = "描述")
	private String descp;

	@Column(name = "seq")
	@RxModelField(text = "序号")
	private Integer seq;

	public PubResourcePo() {
	}

	public PubResourcePo(String id) {
		if (id == null) {
			id = StringUtil.getUUIDPure();
		}
		this.resourceId = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}