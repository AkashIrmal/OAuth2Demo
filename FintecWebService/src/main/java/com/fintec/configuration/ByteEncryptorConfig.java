package com.fintec.configuration;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

public enum ByteEncryptorConfig {

	STANDARD, STRONGER;

	private ByteEncryptorConfig(){}

	public BytesEncryptor getBytesEncryptor(String key, String password){
		switch (this) {
		case STRONGER : return Encryptors.stronger(key,password);
		case STANDARD : 
		default: return Encryptors.standard(key,password);
		}
	}
}