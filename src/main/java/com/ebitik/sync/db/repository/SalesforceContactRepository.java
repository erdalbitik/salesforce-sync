package com.ebitik.sync.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.db.entity.SalesforceContact;

public interface SalesforceContactRepository extends CrudRepository<SalesforceContact, String> {

	SalesforceContact findFirstByAccessAccountOrderByLastModifiedDateDesc(AccessAccount accessAccount);
}
