package com.rx.pub.file.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.rx.pub.file.po.RxFile;
import com.rx.pub.mybatis.impls.MyBatisMapper;

public interface RxFileMapper extends MyBatisMapper<RxFile> {
	
	List<RxFile> searchList(@Param("param") RxFile po);

	int batchSaveFile(@Param("params") List<RxFile> fileList);
}
