package com.ebitik.sync.web.response;

import com.ebitik.sync.db.entity.SalesforceContact;

import lombok.Getter;

@Getter
public class SalesforceContactResponse {

	private String id;

	private String name;

	private String email;

	public SalesforceContactResponse(SalesforceContact contact) {
		this.id = contact.getId();
		this.name = contact.getName();
		this.email = contact.getEmail();
	}

}