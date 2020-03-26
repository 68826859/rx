package com.rx.base.service;

import com.rx.base.page.PageExcuter;
import com.rx.base.page.Pager;
import com.rx.base.page.PagerProvider;
import com.rx.base.vo.ListVo;

//import com.rx.base.vo.ListVo;
import java.util.List;

public interface BaseService<T> {
	
	/**
	 * 获得分页处理者
	 * @return
	 */
	PageExcuter getPageExcuter();
	/**
	 * 获得分页信息提供者
	 * @return
	 */
	PagerProvider getPagerProvider();
    /**
     * 根据主键查询记录
     * @param key
     * @return
     */
	T selectByPrimaryKey(Object key);
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param t
     * @return
     */
    T selectOne(T t);
    /**
     * 根据实体中的属性进行查询，只返回第一条数据,查询条件使用等号
     * @param t
     * @return
     */
    T selectFirst(T t);
    /**
     * 查询单表所有记录
     * @return
     */
    List<T> selectAll();
    /**
     * 根据条件查询多条记录
     * @param t
     * @return
     */
    List<T> select(T t);
    /**
     * 根据条件做分页查询
     * @param t
     * @param page
     * @return
     */
    Pager<T> selectPage(T t,Pager<T> page);
    /**
     * 根据条件查询总数
     * @param t
     * @return
     */
    int selectCount(T t);

    /**
     * 插入一条记录(不判空)
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 插入一条记录(判空)
     * @param t
     * @return
     */
    int insertSelective(T t);
    /**
     * 批量插入
     * @param recordList
     * @return
     */
    int insertList(List<? extends T> recordList);
    /**
     * 根据主键更新记录(不判空)
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键更新记录(判空)
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 根据条件删除记录
     * @param t
     * @return
     */
    int delete(T t);
    /**
     * 根据主键删除记录
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据主键批量删除
     * @param key
     * @return
     */
    int deleteByPrimaryKeys(Object... keys); 
    /**
     * 根据主键批量删除
     * @param key
     * @return
     */
    int deleteByPrimaryKeys(List<Object> keys); 
    
    
    
    List<T> selectByExample(Object example);

    int selectCountByExample(Object example);

    int deleteByExample(Object example);

    int updateByExample(T t,Object example);

    int updateByExampleSelective(T t,Object example);
    
    

    
    
    Pager<ListVo> selectDisplayListPage(Object example,String displayQuery, String[] containKeys,Pager<ListVo> page);

    List<ListVo> selectDisplayList(Object example,String displayQuery, String[] containKeys);
    
    
    //Pager<ListVo> selectSelfFilteredListByPage(String[] query, String[] keys,Pager<ListVo> page);

    //List<ListVo> selectSelfFilteredList(String[] query, String[] keys);
    
}
