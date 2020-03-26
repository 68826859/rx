package com.rx.base.serialize;

import java.io.Serializable;

public interface Snapshot<T> extends Serializable {
	T shot();
	void applyShot(T st);
}
