package com.ebitik.sync.dto;

import java.time.OffsetDateTime;

import com.ebitik.sync.config.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SalesforceContactDTO {

	@JsonProperty(value="Id")
	private String id;

	@JsonProperty(value="Name")
	private String name;

	@JsonProperty(value="Email")
	private String email;

	@JsonProperty(value="LastModifiedDate")
	@JsonDeserialize(using = DateDeserializer.class)
	private OffsetDateTime lastModifiedDate;

}