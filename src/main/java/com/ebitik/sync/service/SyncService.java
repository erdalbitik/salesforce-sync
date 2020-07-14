package com.ebitik.sync.service;

import java.time.OffsetDateTime;

import org.springframework.util.StringUtils;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.util.Constants;
import com.force.api.ApiConfig;
import com.force.api.ForceApi;
import com.force.api.QueryResult;

public interface SyncService {

	void sync(Long accountId);

	default ForceApi getApi(String username, String password) {
		return new ForceApi(new ApiConfig().setUsername(username).setPassword(password));
	}

	default <T> QueryResult<T> fetch(AccessAccount accessAccount, ForceApi api, OffsetDateTime lastRecordModifiedDate,
			String table, String columns, String nextRecordsUrl, Class<T> clazz) {

		if (!StringUtils.isEmpty(nextRecordsUrl)) {
			return api.queryMore(nextRecordsUrl, clazz);
		}

		String dateStr = lastRecordModifiedDate.format(Constants.SALESFORCE_DATE_FORMAT);

		return api.query(String.format("SELECT %s FROM %s WHERE LastModifiedDate >= %s", columns, table, dateStr), clazz);
	}

}
