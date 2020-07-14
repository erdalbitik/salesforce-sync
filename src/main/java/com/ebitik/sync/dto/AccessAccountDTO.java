package com.ebitik.sync.dto;

import com.ebitik.sync.db.entity.AccessAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccessAccountDTO {

	private String label;

	private String username;

	private String password;

	private Integer syncPeriod;

	public AccessAccountDTO(AccessAccount sa) {
		this.label = sa.getLabel();
		this.username = sa.getUsername();
		this.password = sa.getPassword();
		this.syncPeriod = sa.getSyncPeriod();
	}

}
