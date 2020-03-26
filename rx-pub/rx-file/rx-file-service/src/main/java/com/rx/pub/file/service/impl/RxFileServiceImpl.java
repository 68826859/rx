package com.rx.pub.file.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.rx.base.file.FileAccessEnum;
import com.rx.base.file.RxFilePersistencer;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.base.result.type.BusinessException;
import com.rx.pub.file.dto.RxFileDto;
import com.rx.pub.file.dto.UploadDto;
import com.rx.pub.file.mapper.RxFileMapper;
import com.rx.pub.file.po.RxFile;
import com.rx.pub.file.service.RxFileService;
import com.rx.pub.file.utils.FileConverter;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.spring.utils.SpringContextHelper;
import tk.mybatis.mapper.entity.Example;

@Service
public class RxFileServiceImpl extends MybatisBaseService<RxFile> implements RxFileService {
    @Resource
    private RxFileMapper rxFileMapper;

    
    
    @Override
    public UploadDto addUpload(RxFile record){
    	rxFileMapper.insert(record);
    	return new UploadDto(record.getPath(), FileConverter.getFullPathForPath(record.getPath(),null))
				.setFileId(record.getId());
    }
    
    @Override
    public UploadDto addUpload(InputStream is,String fileName,String contentType,long uploadSize,Integer fileAccess, String filePrefix,String creater){
    	RxFile record2 = this.saveFile(is, fileName, contentType, uploadSize,fileAccess,filePrefix, creater);
    	return new UploadDto(record2.getPath(), FileConverter.getFullPathForPath(record2.getPath(),null))
				.setFileId(record2.getId());
    }
    
    
    @Override
    public RxFile saveFile(InputStream is,String fileName,String contentType,long uploadSize,Integer fileAccess, String filePrefix,String creater){
    	byte[] in_b;
    	try{
	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
	    	byte[] buff = new byte[100];
	    	int rc = 0; 
	    	while ((rc = is.read(buff, 0, 100)) > 0) { 
	    		swapStream.write(buff, 0, rc); 
	    	} 
	    	in_b = swapStream.toByteArray();
	    	
	    	swapStream.close();
    	}catch(Exception e){
    		throw new BusinessException("读取文件流异常.");
    	}
    	return this.saveFile(in_b, fileName, contentType, uploadSize,fileAccess,filePrefix, creater);
    }
    
    
    @Override
    public RxFile saveFile(byte[] fileBytes,String fileName,String contentType,long uploadSize,Integer fileAccess, String filePrefix,String creater){
    	String id = DigestUtils.md5DigestAsHex(fileBytes);
    	RxFile record = rxFileMapper.selectByPrimaryKey(id);
    	RxFile record2 = new RxFile();
    	record2.setOwner(creater);
    	record2.setFileName(fileName);
    	record2.setContentType(contentType);
    	record2.setUploadSize(uploadSize);
    	record2.setCreateTime(new Date());
    	if(record == null){
    		String fileExtraName = fileName.substring(fileName.lastIndexOf(".")+1).trim();
    		String key = SpringContextHelper.getBean(RxFilePersistencer.class).save(FileAccessEnum.findByValue(fileAccess,FileAccessEnum.公共读),fileBytes,filePrefix,fileExtraName);
    		record2.setPath(key);
    		record2.setId(id);
    	}else{
    		record2.setId(UUID.randomUUID().toString());
    		record2.setPath(record.getPath());
    	}
    	
    	rxFileMapper.insert(record2);
    	return record2;
    }
    
    @Override
    public RxFile selectFileById(String fileId){
    	return rxFileMapper.selectByPrimaryKey(fileId);
    }
    
    
    @Override
	public Pager<RxFile> listMine(RxFileDto vo) {
	final Example example = new Example(RxFile.class);
	Example.Criteria criteria = example.createCriteria();
	example.setOrderByClause("create_time desc");
	if(vo.getOwner() != null){
			criteria.andEqualTo("owner",vo.getOwner());
	}
	if(vo.getFileName() != null){
			criteria.andLike("fileName", "%"+vo.getFileName()+"%");
	}
	if(vo.getTypeSuffix() != null){
			criteria.andLike("contentType", "%"+vo.getTypeSuffix());
	}
	if(vo.getContentType() != null){
			criteria.andEqualTo("contentType",vo.getContentType());
	}
	if(vo.getMark() != null){
		criteria.andEqualTo("mark",vo.getMark());
	}
	String[] cts = vo.getContentTypes();
	if(cts != null && cts.length > 0){
			criteria.andIn("contentType",Arrays.asList(cts));
	}
		
	Pager<RxFile> pager = this.getPageExcuter().selectByPage(new PageExcute<RxFile>() {
            @Override
            public List<RxFile> excute() {
                return selectByExample(example);
            }
        },this.getPagerProvider().getPager(RxFile.class));
	return pager;
	}
    
	@Override
	public Pager<RxFile> listAll() {
	final Example example = new Example(RxFile.class);
	//Example.Criteria criteria = example.createCriteria();
	example.setOrderByClause("create_time desc");
	Pager<RxFile> pager = this.getPageExcuter().selectByPage(new PageExcute<RxFile>() {
            @Override
            public List<RxFile> excute() {
                return selectByExample(example);
            }
        },this.getPagerProvider().getPager(RxFile.class));
	return pager;
	}
	
	@Override
	public Pager<RxFile> searchPage(RxFile po) {
		Pager<RxFile> pager = this.getPageExcuter().selectByPage(new PageExcute<RxFile>() {
            @Override
            public List<RxFile> excute() {
                return rxFileMapper.searchList(po);
            }
        },this.getPagerProvider().getPager(RxFile.class));
        return pager;
	}

	@Override
	public List<RxFile> searchList(RxFile po) {
		return rxFileMapper.searchList(po);
	}
	
	@Override
	public int batchSaveFile(List<RxFile> fileList) {
		return rxFileMapper.batchSaveFile(fileList);
	}
}
