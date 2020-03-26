package com.rx.pub.pay.util;

import java.util.HashMap;
import java.util.Map;

import com.rx.pub.pay.servface.PayStrategy;

/**
 * 支付策略工厂
 * Created by Martin on 2016/7/01.
 */
public class StrategyFactory {

    private static Map<Integer, PayStrategy> strategyMap = new HashMap<>();

    private StrategyFactory() {
    }

    private static class InstanceHolder {
        public static StrategyFactory instance = new StrategyFactory();
    }

    public static StrategyFactory getInstance() {
        return InstanceHolder.instance;
    }

    public static void put(Integer key, PayStrategy strategy) {
        strategyMap.put(key, strategy);
    }
    
    public PayStrategy creator(Integer type) {
        return strategyMap.get(type);
    }

}
