package com.rx.pub.oss.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.rx.base.file.FileAccessEnum;
import com.rx.base.file.RxFilePersistencer;
import com.rx.base.result.type.BusinessException;

@Component
public class OssFilePersistencer implements RxFilePersistencer {
	
	
	@Override
	public String save(FileAccessEnum fileAccess, byte[] bs, String filePrefix, String fileExtraName) {
		//FileTypeEnum type = FileTypeEnum.findByValue(fileExtraName, null);
        //if(type==null)  throw new BusinessException(String.format("不支持文件:%s 类型上传", fileExtraName));
        InputStream is = new ByteArrayInputStream(bs);
        String nameString = OssHelper.roleSessionName;
		return OssHelper.simpleUpload(is, fileExtraName, fileAccess, filePrefix);
	}

	@Override
	public void delete(String path) {
		OssHelper.delete(path);
	}

	@Override
	public byte[] read(String path) {
		InputStream is = OssHelper.download(path);
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
	    	is.close();
    	}catch(Exception e){
    		throw new BusinessException("读取文件流异常.");
    	}
		return in_b;
	}

}
