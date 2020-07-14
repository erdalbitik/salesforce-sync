package com.ebitik.sync.db.converter;

import java.nio.charset.StandardCharsets;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.util.StringUtils;

import com.ebitik.sync.util.CryptoUtil;
import com.ebitik.sync.util.PropertiesUtil;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String plain) {
		if (StringUtils.isEmpty(plain)) {
			return plain;
		}

		String encryptKey = PropertiesUtil.getValue("encryption.key");
		String encryptSalt = PropertiesUtil.getValue("encryption.salt");

		return CryptoUtil.encrypt(plain, encryptKey, encryptSalt.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String convertToEntityAttribute(String encrypted) {
		if (StringUtils.isEmpty(encrypted)) {
			return encrypted;
		}

		String encryptKey = PropertiesUtil.getValue("encryption.key");
		String encryptSalt = PropertiesUtil.getValue("encryption.salt");

		return CryptoUtil.decrypt(encrypted, encryptKey, encryptSalt.getBytes(StandardCharsets.UTF_8));
	}
}
