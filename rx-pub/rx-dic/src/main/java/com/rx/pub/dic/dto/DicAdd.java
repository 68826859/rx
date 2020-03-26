package com.rx.pub.dic.dto;

//import javax.validation.constraints.NotBlank;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.dic.controller.PubDictionaryController;

@ExtClass(extend = ActionForm.class, alternateClassName = "DicAdd")
public class DicAdd {

	@ExtFormAction
	SpringAction submitAction = new SpringAction(PubDictionaryController.class, "add");

	// @NotBlank(message="名称不能为空")
	@ExtFormField(label = "名称", allowBlank = false)
	private String name;
	// @NotBlank(message="值不能为空")
	@ExtFormField(label = "值", allowBlank = false)
	private String value;

	// @NotBlank(message="所属字典目录不能为空")
	@ExtFormField(label = "所属字典目录", allowBlank = false, comp = Hidden.class)
	private String parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
