package com.rx.base.page;

import com.rx.base.page.Pager;

/**
 * @author Ray
 **/

public interface PagerProvider{
	public <T> Pager<T> getPager(Class<T> clazz) ;
}
