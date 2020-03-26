package com.rx.pub.redis.utils;

public interface Function<E,T> {
	T callback(E e);
}
