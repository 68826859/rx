package com.rx.pub.file.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum PayPermissionEnum implements RxPermissionable{
    新增微信用户信息("pay_wechat_user_add","微信用户信息管理","新增微信用户信息"),
	修改微信用户信息("pay_wechat_user_update","微信用户信息管理","修改微信用户信息"),
	删除微信用户信息("pay_wechat_user_del","微信用户信息管理","删除微信用户信息"),
	微信用户信息列表("pay_wechat_user_list","微信用户信息管理","查询微信用户信息列表"),

	新增商户主体("pay_wechat_account_add","微信商户主体管理","新增商户主体"),
	修改商户主体("pay_wechat_account_update","微信商户主体管理","修改商户主体"),
	删除商户主体("pay_wechat_account_del","微信商户主体管理","删除商户主体"),
	商户主体列表("pay_wechat_account_list","微信商户主体管理","商户主体列表"),

	新增支付信息("pay_wechat_account_pub_add","微信支付信息管理","新增支付信息"),
	修改支付信息("pay_wechat_account_pub_update","微信支付信息管理","修改支付信息"),
	删除支付信息("pay_wechat_account_pub_del","微信支付信息管理","删除支付信息"),
	支付信息列表("pay_wechat_account_pub_list","微信支付信息管理","支付信息列表"),
	重置支付配置项("pay_wechat_account_pub_list","微信支付信息管理","重置支付配置项");
	
	
	@RxModelField(isID=true)
	private String code;

	@RxModelField()
	private String group;

	@RxModelField()
	private String desc;

	PayPermissionEnum(String code,String group,String desc){
		this.code = code;
		this.group = group;
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getGroup() {
		return group;
	}

	
	public String getName() {
		return this.name();
	}
	public String getId() {
		return code;
	}
}