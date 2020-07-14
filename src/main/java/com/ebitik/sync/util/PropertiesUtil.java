package com.ebitik.sync.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesUtil {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream("application.properties");
			properties.load(stream);
		} catch (IOException e) {
			log.error("application.properties file not loaded. Error: {}", e.getMessage());
		}
	}

	public static String getValue(String key) {
		return properties.getProperty(key);
	}

}
