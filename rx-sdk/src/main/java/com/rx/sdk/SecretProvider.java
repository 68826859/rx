package com.rx.sdk;

public interface SecretProvider {

	
	String getSecret(String owner) throws Exception;
	
}
