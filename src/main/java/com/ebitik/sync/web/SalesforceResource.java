package com.ebitik.sync.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.ebitik.sync.service.SalesforceService;
import com.ebitik.sync.web.response.SalesforceResponse;

import lombok.RequiredArgsConstructor;


@Path("/salesforce")
@RequiredArgsConstructor
public class SalesforceResource {

	private final SalesforceService service;

	@GET
	@Path("/{objectId}")
	@Produces("application/json")
	public SalesforceResponse getAccessAccount(@PathParam String objectId) {
		return service.getSalesforceData(objectId);
	}

}
