package com.ebitik.sync.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.ebitik.sync.db.entity.AccessAccount;

public interface AccessAccountRepository extends CrudRepository<AccessAccount, Long> {

	AccessAccount findByUsername(String username);
}
