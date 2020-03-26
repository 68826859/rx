package com.rx.pub.crud.vo;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;
import com.rx.base.enm.RxDatePattern;
import com.rx.pub.crud.enm.CrudTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ExtClass(extend=ParamForm.class,alternateClassName="RecycleSearchVo")
public class RecycleSearchVo {

	@ExtConfig
	Boolean autoCreateBtns = Boolean.TRUE;
	
	@ExtConfig
	Boolean autoSearch = Boolean.TRUE;
	//	@ExtComp
//	Button btn = new Button("清空回收站","removeBeanList"){{
//		style=new Style(){{
//			float_="right";
//			margin="5px 10px 5px 10px";
//		}};
//	}};
	//    @ExtFormField(label="元素类", em = RecycleTypeEnum.class)
//    private String objectClass;

    @ExtFormField(label="名称",em = CrudTypeEnum.class)
    private String objectName;

    //@ExtFormField(label="编号")
    //private String id;

    @ExtFormField(label="回收开始时间", datePattern = RxDatePattern.ISO8601Short)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginTime;

    @ExtFormField(label="回收结束时间", datePattern = RxDatePattern.ISO8601Short)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;
	public Date getBeginTime() {
	return beginTime;
	}
	public RecycleSearchVo setBeginTime(Date beginTime) {
	this.beginTime = beginTime;return this;
	}
	public Date getEndTime() {
	return endTime;
	}
	public RecycleSearchVo setEndTime(Date endTime) {
	this.endTime = endTime;return this;
	}

//    public String getObjectClass() {
//        return objectClass;
//    }
//
//    public RecycleSearchDto setObjectClass(String objectClass) {
//        this.objectClass = objectClass;return this;
//    }

//    public String getObjectName() {
//        return objectName;
//    }
//
//    public RecycleSearchDto setObjectName(String objectName) {
//        this.objectName = objectName;return this;
//    }

}
