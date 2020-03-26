package com.rx.pub.dic.dto;

//import javax.validation.constraints.NotBlank;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.dic.controller.PubDictionaryController;

@ExtClass(extend = ActionForm.class, alternateClassName = "DicUpdate")
public class DicUpdate {

	@ExtFormAction
	SpringAction submitAction = new SpringAction(PubDictionaryController.class, "update");

	//@NotBlank(message = "字典项ID")
	@ExtFormField(label = "ID", allowBlank = false, comp = Hidden.class)
	private String id;
	//@NotBlank(message = "名称不能为空")
	@ExtFormField(label = "名称", allowBlank = false)
	private String name;
	//@NotBlank(message = "值不能为空")
	@ExtFormField(label = "值", allowBlank = false)
	private String value;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
