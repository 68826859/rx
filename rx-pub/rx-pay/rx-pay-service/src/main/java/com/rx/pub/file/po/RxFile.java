package com.rx.pub.file.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.base.file.RxFilePersistencer;
import com.rx.base.model.annotation.RxModelField;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@RxModelField(text = "上传文件表")
@Table(name = "pub_file")
public class RxFile {

	@Id
	@Column(name = "id")
	private String id;
	@Transient
	@RxModelField( tar = "path")
	private String src;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "upload_size")
	private Long uploadSize;

	@Column(name = "path")
	private String path;

	@Column(name = "owner") // 拥有者id，可用PoUtil.getIdByPo()获取
	private String owner;

	@Column(name = "owner_type")
	private String ownerType;

	@Column(name = "mark")
	private String mark;

	@Column(name = "prop")
	private String prop;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	public RxFile() {
	}

	public String getPath() {
		return path;
	}

	public RxFile setPath(String path) {
		this.path = path;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public RxFile setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public Long getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(Long uploadSize) {
		this.uploadSize = uploadSize;
	}

	public String getId() {
		return id;
	}

	public RxFile setId(String id) {
		this.id = id;
		return this;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public byte[] fileBytes() {
		return SpringContextHelper.getBean(RxFilePersistencer.class).read(this.getPath());
	}

}