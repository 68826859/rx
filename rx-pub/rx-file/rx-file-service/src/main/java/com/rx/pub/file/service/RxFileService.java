package com.rx.pub.file.service;

import java.io.InputStream;
import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.file.dto.RxFileDto;
import com.rx.pub.file.dto.UploadDto;
import com.rx.pub.file.po.RxFile;

/**
 *
 **/
public interface RxFileService extends BaseService<RxFile> {

	/**
	 * 保存一个文件流
	 * 
	 * @param fileBytes
	 * @param fileName
	 * @param contentType
	 * @param uploadSize
	 * @param creater
	 * @return 保存成功的文件属性对象
	 */
	public RxFile saveFile(byte[] fileBytes, String fileName, String contentType, long uploadSize, Integer fileAccess,
			String filePrefix, String creater);

	/**
	 * 保存一个文件流
	 * 
	 * @param is
	 * @param fileName
	 * @param contentType
	 * @param uploadSize
	 * @param creater
	 * @return 保存成功的文件属性对象
	 */
	public RxFile saveFile(InputStream is, String fileName, String contentType, long uploadSize, Integer fileAccess,
			String filePrefix, String creater);

	/**
	 *
	 * @param is
	 * @param fileName
	 * @param contentType
	 * @param uploadSize
	 * @param creater
	 * @return
	 */
	UploadDto addUpload(InputStream is, String fileName, String contentType, long uploadSize, Integer fileAccess,
			String filePrefix, String creater);

	UploadDto addUpload(RxFile record);

	public RxFile selectFileById(String fileId);

	public Pager<RxFile> listMine(RxFileDto vo);

	public Pager<RxFile> listAll();

	Pager<RxFile> searchPage(RxFile po);

	List<RxFile> searchList(RxFile po);

	int batchSaveFile(List<RxFile> fileList);

}
