package com.ebitik.sync.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.entity.SalesforceAccount;

public interface SalesforceAccountRepository extends CrudRepository<SalesforceAccount, String> {

	SalesforceAccount findFirstByAccessAccountOrderByLastModifiedDateDesc(AccessAccount accessAccount);
}
