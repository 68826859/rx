package com.rx.pub.redis.utils;
import java.util.List;

//多机加锁服务
public class LockService {
    private final static String KEY_INIT_PRE = "init:";
    private final static String KEY_SYNC_KEY = "REDIS_LOCK";
    private final static String KEY_EXIST_FLAG = "exist";
    private final static String KEY_VALUE = "ticket";
    private final static int TIME_OVER_SECOND = 30;
    
    
    /*
//    加写锁
    public static void Lock(String key, String field) throws Exception {
        String lockString = key + ":" + field;
        String initString = KEY_INIT_PRE + lockString;
        synchronized (KEY_SYNC_KEY){
            String lockInfo = RedisCacher.get(initString);
            if (!KEY_EXIST_FLAG.equals(lockInfo)){
                RedisCacher.set(initString, KEY_EXIST_FLAG, 86400);
                RedisCacher.lpush(lockString, KEY_VALUE);
            }
        }
        List<String> pop = RedisCacher.brpop(TIME_OVER_SECOND, lockString);
        if (pop == null){
//            超时无视锁继续执行
        }
        long count = RedisCacher.llen(lockString);
        if (count != 0){
            RedisCacher.ltrim(lockString, 0, -1);
        }
    }
//    解锁
    public static void Unlock(String key, String field) throws Exception {
        String lockString = key + ":" + field;
        RedisCacher.lpush(lockString, "ticket");
    }*/
}

