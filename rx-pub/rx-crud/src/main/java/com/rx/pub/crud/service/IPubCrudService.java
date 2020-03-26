package com.rx.pub.crud.service;


import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.crud.dto.CrudSearchDto;
import com.rx.pub.crud.po.PubCrud;
	public interface IPubCrudService extends BaseService<PubCrud> {
		
		
		void restoreRecycle(String id) throws Exception;
		
		void deleteRecycle(String id) throws Exception;
		
		
		List<PubCrud> listRecycle(CrudSearchDto searchDto);
		
		Pager<PubCrud> listRecyclePage(CrudSearchDto searchDto);
		
		
		List<PubCrud> listCrud(CrudSearchDto searchDto);
		
		Pager<PubCrud> listCrudPage(CrudSearchDto searchDto);
}
