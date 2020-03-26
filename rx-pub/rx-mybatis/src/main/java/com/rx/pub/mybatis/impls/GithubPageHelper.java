package com.rx.pub.mybatis.impls;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rx.base.page.PageExcute;
import com.rx.base.page.PageExcuter;
import com.rx.base.page.Pager;


@Component
public class GithubPageHelper implements PageExcuter{

	@Override
	public <F> Pager<F> selectByPage(PageExcute<F> pageExcute, Pager<F> pager) {
		
        PageHelper.startPage(pager.getPageNum(), pager.getPageSize(), true);
        PageInfo<F> pageInfo = new PageInfo<F>(pageExcute.excute());
        pager.setList(pageInfo.getList());//
        
        pager.setPageNum(pageInfo.getPageNum());//
        pager.setPageSize(pageInfo.getPageSize());//
        pager.setSize(pageInfo.getSize());
        pager.setStartRow(pageInfo.getStartRow());
        pager.setEndRow(pageInfo.getEndRow());
        
        pager.setPages(pageInfo.getPages());//
        pager.setPrePage(pageInfo.getPrePage());
        pager.setNextPage(pageInfo.getNextPage());
        pager.setIsFirstPage(pageInfo.isIsFirstPage());
        pager.setIsLastPage(pageInfo.isIsLastPage());
        pager.setHasPreviousPage(pageInfo.isHasPreviousPage());
        pager.setHasNextPage(pageInfo.isHasNextPage());
        pager.setNavigatePages(pageInfo.getNavigatePages());
        pager.setNavigatepageNums(pageInfo.getNavigatepageNums());
        pager.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
        pager.setNavigateLastPage(pageInfo.getNavigateLastPage());
        pager.setTotal(pageInfo.getTotal());//
        return pager;
	}
	
	
}
