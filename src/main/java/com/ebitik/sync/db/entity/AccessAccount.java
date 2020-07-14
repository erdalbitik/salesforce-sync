package com.ebitik.sync.db.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ebitik.sync.db.converter.EncryptionConverter;

import lombok.Data;

@Entity
@Table(name="access_account")
@Data
public class AccessAccount {

	@Id
	@GeneratedValue
	private Long id;

	private String label;

	private String username;

	@Convert(converter = EncryptionConverter.class)
	private String password;

	@Column(name = "sync_period")
	private Integer syncPeriod;//in seconds

	private Boolean scheduled = Boolean.FALSE;

}
