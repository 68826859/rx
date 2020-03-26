package com.rx.pub.mybatis.plugins;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import com.rx.base.model.RxModelDeleter;
import com.rx.base.model.RxModelInserter;
import com.rx.base.model.RxModelUpdater;

@Intercepts(value = {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class HandleUpdatePlugin implements Interceptor {
    
    public Object intercept(Invocation invocation) throws Throwable {
    	
        //MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //String sqlId = mappedStatement.getId();  
        //String namespace = sqlId.substring(0, sqlId.indexOf('.'));  
        //Executor exe = (Executor) invocation.getTarget();
        if ("update".equals(invocation.getMethod().getName())) {
            MappedStatement state = (MappedStatement) invocation.getArgs()[0];
            SqlCommandType sqlType = state.getSqlCommandType();
            Object parameter = invocation.getArgs()[1];
            Class<?> entityClass = getEntityClass(((MappedStatement) invocation.getArgs()[0]));
            if (sqlType == SqlCommandType.INSERT) {
            	RxModelInserter.beforeInsertModel(parameter,entityClass);
            	Object obj = invocation.proceed();
            	RxModelInserter.afterInsertModel(parameter,entityClass,obj);
            	return obj;
            }else if(sqlType == SqlCommandType.UPDATE) {
            	RxModelUpdater.beforeUpdateModel(parameter,entityClass);
            	Object obj = invocation.proceed();
            	RxModelUpdater.afterUpdateModel(parameter,entityClass,obj);
            	return obj;
            }else if(sqlType == SqlCommandType.DELETE) {
            	RxModelDeleter.beforeDeleteModel(parameter,entityClass);
            	Object obj = invocation.proceed();
            	RxModelDeleter.afterDeleteModel(parameter,entityClass,obj);
            	return obj;
            }
        }
        return invocation.proceed();
    }
    
    public static Class<?> getMapperClass(String msId) {
        if (msId.indexOf(".") == -1) {
            throw new RuntimeException("当前MappedStatement的id=" + msId + ",不符合MappedStatement的规则!");
        }
        String mapperClassStr = msId.substring(0, msId.lastIndexOf("."));
        try {
            return Class.forName(mapperClassStr);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    private static Map<String, Class<?>> entityClassMap = new HashMap<String, Class<?>>();
    
    public static Class<?> getEntityClass(MappedStatement ms) {
        String msId = ms.getId();
        if (entityClassMap.containsKey(msId)) {
            return entityClassMap.get(msId);
        } else {
            Class<?> mapperClass = getMapperClass(msId);
            Type[] types = mapperClass.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType t = (ParameterizedType) type;
                    Class<?> returnType = (Class<?>) t.getActualTypeArguments()[0];
                    entityClassMap.put(msId, returnType);
                    return returnType;
                }
            }
        }
        throw new RuntimeException("无法获取Mapper<T>泛型类型:" + msId);
    }

    @Override
    public Object plugin(Object target) {
    	
    	if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    private Properties properties;
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
