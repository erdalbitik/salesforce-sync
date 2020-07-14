package com.ebitik.sync.service;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import com.ebitik.sync.db.repository.SalesforceAccountRepository;
import com.ebitik.sync.db.repository.SalesforceContactRepository;
import com.ebitik.sync.db.repository.SalesforceLeadRepository;
import com.ebitik.sync.web.response.SalesforceAccountResponse;
import com.ebitik.sync.web.response.SalesforceContactResponse;
import com.ebitik.sync.web.response.SalesforceLeadResponse;
import com.ebitik.sync.web.response.SalesforceResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class SalesforceService {

	private final SalesforceAccountRepository sfAccountRepository;
	private final SalesforceContactRepository sfContactRepository;
	private final SalesforceLeadRepository sfLeadRepository;

	public SalesforceResponse getSalesforceData(String objectId) {
		log.debug("getSalesforceData called!");

		SalesforceAccountResponse accResp = sfAccountRepository.findById(objectId).filter(Objects::nonNull).map(SalesforceAccountResponse::new).orElse(null);
		SalesforceContactResponse contResp = sfContactRepository.findById(objectId).filter(Objects::nonNull).map(SalesforceContactResponse::new).orElse(null);
		SalesforceLeadResponse leadResp = sfLeadRepository.findById(objectId).filter(Objects::nonNull).map(SalesforceLeadResponse::new).orElse(null);

		return new SalesforceResponse(accResp, contResp, leadResp);
	}
}
