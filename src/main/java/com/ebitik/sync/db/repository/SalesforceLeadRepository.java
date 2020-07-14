package com.ebitik.sync.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.entity.SalesforceLead;

public interface SalesforceLeadRepository extends CrudRepository<SalesforceLead, String> {

	SalesforceLead findFirstByAccessAccountOrderByLastModifiedDateDesc(AccessAccount accessAccount);
}
