package com.ebitik.sync.db.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="salesforce_contact")
@Data
public class SalesforceContact {

	@Id
	private String id;

	private String name;

	private String email;

	@Column(name = "last_modified_date")
	private OffsetDateTime lastModifiedDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "access_account_id", nullable = false)
	private AccessAccount accessAccount;

}