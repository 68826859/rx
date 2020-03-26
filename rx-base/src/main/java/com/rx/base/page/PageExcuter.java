package com.rx.base.page;

import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;

/**
 * @author Ray
 **/

public interface PageExcuter{
	public <F> Pager<F> selectByPage(PageExcute<F> pageExcute,Pager<F> pager);
}
