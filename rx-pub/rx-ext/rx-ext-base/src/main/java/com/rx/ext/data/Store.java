package com.rx.ext.data;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.proxy.Proxy;

import java.util.List;

@ExtClass(alias = "store.store")
public class Store<T> extends ProxyStore<T> {

    public Store() {
    }

    public Store(Class<? extends T> model, Proxy proxy) {
        super(model, proxy);
    }

    @ExtConfig()
    private List<? extends T> data;


    @ExtConfig
    public Integer pageSize;

    public List<? extends T> getData() {
        return data;
    }

    public void setData(List<? extends T> data) {
        this.data = data;
    }


}
