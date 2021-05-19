package com.rx.base.convert;

public interface RxConvertProvider<T> {
	T convert(RxConvertable f);
}
