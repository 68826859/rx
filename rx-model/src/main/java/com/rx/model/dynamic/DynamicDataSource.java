package com.rx.model.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.rx.spring.utils.PropertiesHelper;

import javax.sql.DataSource;
import java.util.*;
	public class DynamicDataSource extends AbstractRoutingDataSource {

    // 线程本地环境
    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();
    // 可选取slave的keys
    private List<String> slaveDataSourcesKeys;
    // 可选取master的keys
    private List<String> masterDataSourcesKeys;
    //从库数据源
    private Map<String, DataSource> slavetDataSources;
    //主库数据源
    private Map<String, DataSource> masterDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceHolder.get();
    }

    @Override
    public void afterPropertiesSet(){
        Map<Object, Object> allDataSources ;
        if (null==slavetDataSources) {
            allDataSources = new HashMap<>(masterDataSources.size());
        }else {
            allDataSources = new HashMap<>(masterDataSources.size()+slavetDataSources.size());
            allDataSources.putAll(slavetDataSources);
        }
        allDataSources.putAll(masterDataSources);
        super.setTargetDataSources(allDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 注册slave datasource
     *
     * @param slavetDataSources
     */
    public void setSlavetDataSources(Map<String, DataSource> slavetDataSources) {
        if (slavetDataSources == null || slavetDataSources.size() == 0) {
            return;
        }

        this.slavetDataSources = slavetDataSources;
        slaveDataSourcesKeys = new ArrayList<>(slavetDataSources.size());
        for (Map.Entry<String, DataSource> entry : slavetDataSources.entrySet()) {
            slaveDataSourcesKeys.add(entry.getKey());
        }
    }
    /**
     * 注册master datasource
     *
     * @param masterDataSources
     */
    public void setMasterDataSources(Map<String, DataSource> masterDataSources) {
        if (masterDataSources == null) {
            throw new IllegalArgumentException("Property 'masterDataSources' is required");
        }
        this.masterDataSources = masterDataSources;
        this.masterDataSourcesKeys = new ArrayList<>(masterDataSources.size());
        for (Map.Entry<String, DataSource> entry : masterDataSources.entrySet()) {
            masterDataSourcesKeys.add(entry.getKey());
        }
    }

    /**
     * 标记选取从库数据源
     */
    public void markSlave() {
        if(null==dataSourceHolder.get())setDataSource(selectFromSlave());
    }

    /**
     * 标记选取主库数据源
     */
    public void markMaster() {
        if(null==dataSourceHolder.get()) setDataSource(selectFromMaster());
    }

    public void reset(){
        setDataSource(null);
    }

    /**
     * 随机选取slave
     */
    private String selectFromSlave() {
        return slavetDataSources == null?selectFromMaster():slaveDataSourcesKeys.get(new Random().nextInt(slaveDataSourcesKeys.size()));
    }

    /**
     * 随机选取master
     */
    private String selectFromMaster() {
        return masterDataSourcesKeys.get(new Random().nextInt(masterDataSourcesKeys.size()));
    }

    private void setDataSource(String dataSourceKey) {
        dataSourceHolder.set(dataSourceKey);
    }

}
