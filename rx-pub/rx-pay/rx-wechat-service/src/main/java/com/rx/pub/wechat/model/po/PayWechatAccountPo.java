package com.rx.pub.wechat.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.utils.StringUtil;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;

/**
 * 商户主体(PayWechatAccount)实体类
 *
 * @author klf
 * @date 2020-01-09 20:08:43
 */
@RxModel(text = "商户主体")
@Table(name = "pay_wechat_account")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountPo")
public class PayWechatAccountPo implements Serializable {
    private static final long serialVersionUID = 608033697720382393L;
    
    @Id
    @RxModelField(text = "", isID = true)
    @Column(name = "id")
    private String id;
    
        
    @Column(name = "account_code")
    @RxModelField(text = "编码")
    private String accountCode;
            
        
    @Column(name = "app_id")
    @RxModelField(text = "微信开放平台appid")
    private String appId;
            
        
    @Column(name = "mch_id")
    @RxModelField(text = "公众平台商户号")
    private String mchId;
            
        
    @Column(name = "api_key")
    @RxModelField(text = "商户密钥")
    private String apiKey;
            
        
    @Column(name = "key_path")
    @RxModelField(text = "商户证书")
    private String keyPath;
            
        
    @Column(name = "status")
	@RxModelField(text = "状态", em = StatusEnum.class)
    private Integer status;
             
        
    @Column(name = "create_time")
    @RxModelField(text = "", datePattern = RxDatePattern.ISO8601Long)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
        
    public PayWechatAccountPo() { }

    public PayWechatAccountPo(String id) {
        if (id == null) {
            id = StringUtil.getUUIDPure();
        }
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
                                                  
