package com.ebitik.sync.config;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class JacksonObjectMapperConfig implements ObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper mapper) {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		mapper.registerModule(new JavaTimeModule());
	}

}