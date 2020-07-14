package com.ebitik.sync.config;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.ebitik.sync.util.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<OffsetDateTime> {

	@Override
	public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException {
		return OffsetDateTime.parse(p.getText(), Constants.SALESFORCE_DATE_FORMAT);
	}}