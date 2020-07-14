package com.ebitik.sync.web.response;

import java.math.BigDecimal;

import com.ebitik.sync.db.entity.SalesforceAccount;

import lombok.Getter;

@Getter
public class SalesforceAccountResponse {

	private String id;

	private String name;

	private BigDecimal annualRevenue;

	public SalesforceAccountResponse(SalesforceAccount account) {
		this.id = account.getId();
		this.name = account.getName();
		this.annualRevenue = account.getAnnualRevenue();
	}

}