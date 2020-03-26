package com.rx.model.dynamic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
	public class DataSourceAspect {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    private List<String> masterMethodPattern = new ArrayList<>();

    private static final String[] defaultMasterMethodStart = new String[]{ "add", "insert", "update","del" };

    /**
     * 在进入Service方法之前执行
     *
     * @param point 切面对象
     */
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        boolean isMaster = false;
        if(masterMethodPattern.isEmpty()){
            isMaster=isMaster(methodName);
        }else {
            // 使用策略规则匹配
            for (String mappedName : masterMethodPattern) {
                if (isMatch(mappedName,methodName)) {
                    isMaster = true;
                    break;
                }
            }
        }
        if (isMaster) {
            // 标记为写库
            dynamicDataSource.markMaster();
        } else {
            // 标记为读库
            dynamicDataSource.markSlave();
        }
        try {
            return point.proceed();
        } finally {
            dynamicDataSource.reset();
        }
    }

    /** 
     * 判断是否为写库
     *
     * @param methodName
     * @return
     */
    private Boolean isMaster(String methodName) {
    	
    	for(String item : defaultMasterMethodStart) {
    		if(methodName.startsWith(item)) {
    			return true;
    		}
    	}
    	return false;
    }

    protected boolean isMatch(String mappedName,String methodName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }


    /**
     * 读取事务管理中的策略
     *
     * @param txAdvice
     * @throws Exception
     */
    public void setTxAdvice(TransactionInterceptor txAdvice) throws Exception {
        if (txAdvice == null) {
            // 没有配置事务管理策略
            return;
        }
        //从txAdvice获取到策略配置信息
        TransactionAttributeSource transactionAttributeSource = txAdvice.getTransactionAttributeSource();
        if (!(transactionAttributeSource instanceof NameMatchTransactionAttributeSource)) {
            return;
        }
        //使用反射技术获取到NameMatchTransactionAttributeSource对象中的nameMap属性值
        NameMatchTransactionAttributeSource matchTransactionAttributeSource = (NameMatchTransactionAttributeSource) transactionAttributeSource;
        Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
        nameMapField.setAccessible(true); //设置该字段可访问
        //获取nameMap的值
        Map<String, TransactionAttribute> map = (Map<String, TransactionAttribute>) nameMapField.get(matchTransactionAttributeSource);

        //遍历nameMap
        for (Map.Entry<String, TransactionAttribute> entry : map.entrySet()) {
            if (entry.getValue().isReadOnly()) {//判断之后定义了不是ReadOnly的策略才加入到masterMethodPattern
                continue;
            }
            masterMethodPattern.add(entry.getKey());
        }
    }

}
