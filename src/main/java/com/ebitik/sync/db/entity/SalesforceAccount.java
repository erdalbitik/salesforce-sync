package com.ebitik.sync.db.entity;

import java.math.BigDecimal;
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
@Table(name="salesforce_account")
@Data
public class SalesforceAccount {

	@Id
	private String id;

	private String name;

	@Column(name = "annual_revenue")
	private BigDecimal annualRevenue;

	@Column(name = "external_id")
	private String externalId;

	@Column(name = "last_modified_date")
	private OffsetDateTime lastModifiedDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "access_account_id", nullable = false)
	private AccessAccount accessAccount;

}