package com.rx.pub.mybatis.impls;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.base.result.type.BusinessException;
import com.rx.base.utils.StringUtil;
import com.rx.base.vo.ListVo;
import com.rx.base.vo.TreeVo;
import com.rx.spring.SpringBaseService;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;

public class MybatisBaseService<T> extends SpringBaseService<T> {
	
	@Autowired
    private MyBatisMapper<T> myBatisMapper;
	
	
	public MyBatisMapper<T> getMyBatisMapper() {
		return myBatisMapper;
	}
	
	@Override
    public T selectByPrimaryKey(Object key) {
        return getMyBatisMapper().selectByPrimaryKey(key);
    }

    @Override
    public T selectOne(T t) {
        return getMyBatisMapper().selectOne(t);
    }

    @Override
    public T selectFirst(T t) {
    	List<T> list = getMyBatisMapper().select(t);
    	if(list.size() > 0) {
    		return list.get(0);
    	}
    	return null;
    }
    
    @Override
    public List<T> selectAll() {
        return getMyBatisMapper().selectAll();
    }

    @Override
    public int selectCount(T t) {
        return getMyBatisMapper().selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return getMyBatisMapper().select(t);
    }
    
    @Override
    public Pager<T> selectPage(T t,Pager<T> page) {
    	
    	if(page == null) {
    		page = (Pager<T>)this.getPagerProvider().getPager(t.getClass());
    	}
    	
    	return getPageExcuter().selectByPage(new PageExcute<T>() {
            @Override
            public List<T> excute() {
                return getMyBatisMapper().select(t);
            }
        },page);
    	
    }

    @Override
    public int insert(T t) {
        return getMyBatisMapper().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getMyBatisMapper().insertSelective(t);
    }

	@Override
	public int insertList(List<? extends T> recordList) {
		MyBatisMapper<T> mapper = getMyBatisMapper();
		if(mapper instanceof MySqlMapper) {
			MySqlMapper<T> mapper2 = (MySqlMapper<T>)mapper;
			return mapper2.insertList(recordList);
		}
		int res = 0;
		for(T t : recordList) {
			mapper.insertSelective(t);
			res++;
		}		
		return res;
	}
    
    @Override
    public int updateByPrimaryKey(T t) {
        return getMyBatisMapper().updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getMyBatisMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public int delete(T t) {
        return getMyBatisMapper().delete(t);
    }


    @Override
    public int deleteByPrimaryKey(Object key) {
        return getMyBatisMapper().deleteByPrimaryKey(key);
    }

	@Override
	public int deleteByPrimaryKeys(Object... keys) {
		MyBatisMapper<T> mapper = getMyBatisMapper();
		if(mapper instanceof IdsMapper) {
			IdsMapper<T> mapper2 = (IdsMapper<T>)mapper;
			return mapper2.deleteByIds(StringUtil.join(keys, ",", true));
		}
		int res = 0;
		for(Object t : keys) {
			res += mapper.deleteByPrimaryKey(t);
		}		
		return res;
	}
	
	@Override
	public int deleteByPrimaryKeys(List<Object> keys) {
		MyBatisMapper<T> mapper = getMyBatisMapper();
		if(mapper instanceof IdsMapper) {
			IdsMapper<T> mapper2 = (IdsMapper<T>)mapper;
			return mapper2.deleteByIds(StringUtil.join(keys, ",", true));
		}
		int res = 0;
		for(Object t : keys) {
			res += mapper.deleteByPrimaryKey(t);
		}		
		return res;
	}
	
	
    @Override
    public int selectCountByExample(Object example) {
        return getMyBatisMapper().selectCountByExample(example);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return getMyBatisMapper().selectByExample(example);
    }

    @Override
    public int deleteByExample(Object example) {
        return getMyBatisMapper().deleteByExample(example);
    }

	@Override
    public int updateByExample(T t, Object example) {
        return getMyBatisMapper().updateByExample(t, example);
    }

    @Override
    public int updateByExampleSelective(T t, Object example) {
        return getMyBatisMapper().updateByExampleSelective(t, example);
    }
    
    @Override
    public List<ListVo> selectDisplayList(Object example,String query, String[] keys) {
    	Example example2 = null;
    	Class<?> modelClass = getModelClass();
    	if(example == null) {
            example2 = new Example(modelClass);
    	}else {
    		example2 = (Example)example;
    	}
        Example.Criteria criteria = example2.createCriteria();
        Field[] fields = modelClass.getDeclaredFields();
        
        Field[] matchField = new Field[3];

        for (Field field : fields) {
            Id idAno = field.getAnnotation(Id.class);
            if (idAno != null) {
                if (matchField[0] == null){
                    matchField[0] = field;
                }
                if (matchField[1] == null){
                    matchField[1] = field;
                }
            }
            RxModelField rxModelField = field.getAnnotation(RxModelField.class);
            if (rxModelField != null) {
                if (rxModelField.isID()) {
                    matchField[0] = field;
                    if (matchField[1] == null){
                        matchField[1] = field;
                    }
                }
                if (rxModelField.isDisplay()) {
                    matchField[1] = field;
                }
                if (rxModelField.isParentId()) {
                    matchField[2] = field;
                }
            }
        }
        List<String> strings = new ArrayList<>();
        for (Field eachField : matchField){
            if (eachField != null) {
                strings.add(eachField.getName());
                eachField.setAccessible(true);
            }
        }
        if (matchField[0] == null) {
            throw new BusinessException("配置有误，模型缺少主键标识");
        }

        example2.selectProperties(strings.toArray(new String[]{}));
        List<ListVo> listVoList = new ArrayList<ListVo>();

        if (keys != null && keys.length > 0) {
            criteria.orIn(matchField[0].getName(), Arrays.asList(keys));
        }
        if (query != null) {
            criteria.andLike(matchField[1].getName(), "%" + query + "%");
        }

        List<T> poList = this.getMyBatisMapper().selectByExample(example2);
        for (T po : poList) {
            ListVo listVo;
            if (matchField[2] != null) {
                listVo = new TreeVo();
                try {
                    ((TreeVo) listVo).setParentId(String.valueOf(matchField[2].get(po)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                listVo = new ListVo();
            }
            listVoList.add(listVo);
            try {
                listVo.setKey(String.valueOf(matchField[0].get(po)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                listVo.setDisplay(String.valueOf(matchField[1].get(po)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return listVoList;
    }
    
    @Override
    public Pager<ListVo> selectDisplayListPage(Object example,String query,String[] keys,Pager<ListVo> page) {
        return this.getPageExcuter().selectByPage(new PageExcute<ListVo>() {
            @Override
            public List<ListVo> excute() {
                return selectDisplayList(example,query, keys);
            }
        },page);
    }
    
    
    protected Class<?> getModelClass() {
        Class<?> modelClass = null;
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            try {
                modelClass = (Class<?>) pType.getActualTypeArguments()[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (modelClass == null) {
            throw new BusinessException(this.getClass().getName()+"找不到对应的泛型类模型");
        }
        return modelClass;
    }
    
}
