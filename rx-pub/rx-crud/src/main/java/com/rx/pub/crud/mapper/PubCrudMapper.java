package com.rx.pub.crud.mapper;

import java.util.List;
import com.rx.pub.crud.dto.CrudSearchDto;
import com.rx.pub.crud.po.PubCrud;
import com.rx.pub.mybatis.impls.MyBatisMapper;

public interface PubCrudMapper extends MyBatisMapper<PubCrud>{
	
	List<PubCrud> listCrud(CrudSearchDto param);
	
	List<PubCrud> listRecycle(CrudSearchDto param);
	
}
