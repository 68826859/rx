package com.rx.pub.dic.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;
import com.rx.base.model.annotation.RxModelField;
import com.rx.web.user.UserApplyer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@RxModelField(text = "公共字典表")
@Table(name = "pub_dictionary")
@ExtClass(extend = Model.class)
public class PubDictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @RxModelField(isID = true)
    private String id;

    @Column(name = "name")
    @RxModelField(text = "名称", isDisplay = true)
    @ExtGridColumn()
    private String name;

    @Column(name = "value")
    @RxModelField(text = "值")
    @ExtGridColumn()
    private String value;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "seq")
    private Integer seq;

    @RxModelField(applyer = UserApplyer.class)
    @Column(name = "creater")
    private String creater;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

}
