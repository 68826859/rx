package com.rx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import com.rx.base.page.PageExcuter;
import com.rx.base.page.PagerProvider;
import com.rx.base.service.BaseService;

public abstract class SpringBaseService<T> implements BaseService<T> {
	
    @Autowired
    private PageExcuter pageExcuter;
    
    @Autowired
    private PagerProvider pagerProvider;
    
    
	@Override
	public PageExcuter getPageExcuter() {
		return pageExcuter;
	}
	
	@Override
	public PagerProvider getPagerProvider() {
		return pagerProvider;
	}

}
