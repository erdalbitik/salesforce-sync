package com.ebitik.sync.web.response;

import com.ebitik.sync.db.entity.SalesforceLead;

import lombok.Getter;

@Getter
public class SalesforceLeadResponse {

	private String id;

	private String name;

	private String email;

	public SalesforceLeadResponse(SalesforceLead lead) {
		this.id = lead.getId();
		this.name = lead.getName();
		this.email = lead.getEmail();
	}

}