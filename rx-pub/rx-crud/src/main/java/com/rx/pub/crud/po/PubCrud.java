package com.rx.pub.crud.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.crud.enm.CrudTypeEnum;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.web.user.UserApplyer;
import com.rx.web.user.UserTypeApplyer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@RxModel(text = "数据增删改查记录")
@Table(name = "pub_crud")
@ExtClass(extend = Model.class, alternateClassName = "PubCrud")
public class PubCrud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @RxModelField(isID = true)
    private String id;

    @Column(name = "data_class")
    @RxModelField(text = "数据类型")
    private String dataClass;

    @Column(name = "data_id")
    @RxModelField(text = "数据主键")
    private String dataId;

    @Column(name = "data")
    @RxModelField(text = "数据")
    private String data;

    @Column(name = "crud_type")
    @RxModelField(text="操作类型",em=CrudTypeEnum.class)
    private Integer crudType;

    @RxModelField(text="操作者",applyer = UserApplyer.class)
    @Column(name = "creater")
    private String creater;
    
    @RxModelField(text="操作者类型",applyer=UserTypeApplyer.class)
    @Column(name = "creater_class")
    private String createrClass;
    
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @RxModelField(text="操作时间")
    @Column(name = "create_time")
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getDataClass() {
		return dataClass;
	}

	public void setDataType(String dataClass) {
		this.dataClass = dataClass;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getCrudType() {
		return crudType;
	}

	public void setCrudType(Integer crudType) {
		this.crudType = crudType;
	}

	public String getCreaterClass() {
		return createrClass;
	}

	public void setCreaterClass(String createrClass) {
		this.createrClass = createrClass;
	}

	public void setDataClass(String dataClass) {
		this.dataClass = dataClass;
	}

}
