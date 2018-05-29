package com.fintec.configuration;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;


public enum TextEncryptorConfig{
	
	DEFAULT, TEXT, DELUX, QUERYABLETEXT;

	private TextEncryptorConfig(){}

	public TextEncryptor getTextEncryptor(String key, String password){
		switch (this) {
		case TEXT : return Encryptors.text(key,password);
		case DELUX : return Encryptors.delux(key,password);
		case QUERYABLETEXT : return Encryptors.queryableText(key,password);
		case DEFAULT : 
		default: return Encryptors.noOpText();
		}
	}
}

