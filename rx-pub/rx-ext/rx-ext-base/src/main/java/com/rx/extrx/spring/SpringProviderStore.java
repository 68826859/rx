package com.rx.extrx.spring;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Ajax;
import com.rx.ext.data.reader.Json;
import com.rx.extrx.widget.ServerMethod;
import com.rx.extrx.widget.ServerProvider;

@ExtClass(alias = "store.springproviderstore")
public class SpringProviderStore<T> extends Store<T> {


    //@ExtConfig
    private Class<?> providerClass;

    //@ExtConfig
    private String methodName;

    public SpringProviderStore() {

    }

    public SpringProviderStore(Class<? extends T> model, String providerClass, String methodName) throws ClassNotFoundException {
        this(model, Class.forName(providerClass), methodName);
    }

    public SpringProviderStore(Class<? extends T> model, Class<?> providerClass, String methodName) {
        this.setModel(model);
        ServerProvider sp = null;
        try {
            sp = (ServerProvider) Base.forClass(providerClass);
            //sp.applySelf();
            sp.applyTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServerMethod sm = sp.getMethod(methodName);
        Ajax ajax = new Ajax();
        ajax.setUrl(sm.getUrl());
        Json json = new Json();
        json.setRootProperty("data");
        ajax.setReader(json);
        ajax.setMethod(sm.getMethod());
        this.setProxy(ajax);
        //this.pageSize = 17;
    }


    public Ajax getProxy() {
        return (Ajax) super.getProxy();
    }

    public Class<?> getProviderClass() {
        return providerClass;
    }

    public void setProviderClass(Class<?> providerClass) {
        this.providerClass = providerClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
