package com.ebitik.sync.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesforceResponse {

	private SalesforceAccountResponse account;

	private SalesforceContactResponse contact;

	private SalesforceLeadResponse lead;

}
