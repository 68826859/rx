package com.rx.pub.mybatis.plugins;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.rx.base.model.RxModelSelecter;

@Intercepts(value = {@Signature(args = {Statement.class}, method = "handleResultSets", type = ResultSetHandler.class)})
@Component
public class HandleResultPlugin implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
    	Object obj = invocation.proceed();
    	if(obj instanceof List) {
    		RxModelSelecter.afterSelectModel(null,null,obj);
    	}
    	return obj;
    	
    	/*
    	MappedStatement state = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlType = state.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        Class<?> entityClass = HandleUpdatePlugin.getEntityClass(((MappedStatement) invocation.getArgs()[0]));
        RxModelSelecter.beforeSelectModel(parameter, entityClass);
        Object obj = invocation.proceed();
        RxModelSelecter.afterSelectModel(obj, entityClass,obj);
        return obj;
        */
    }

	@Override
	public Object plugin(Object target) {
		if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
