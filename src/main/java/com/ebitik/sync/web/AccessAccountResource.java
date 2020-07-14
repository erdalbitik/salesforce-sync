package com.ebitik.sync.web;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.ebitik.sync.db.entity.AccessAccount;
import com.ebitik.sync.dto.AccessAccountDTO;
import com.ebitik.sync.service.AccessAccountService;

import lombok.RequiredArgsConstructor;


@Path("/account")
@RequiredArgsConstructor
public class AccessAccountResource {

	private final AccessAccountService service;

	@POST
	@Produces("application/json")
	public AccessAccountDTO createSyncAccount(AccessAccountDTO dto) {
		AccessAccount syncAccount = service.addAccessAccount(dto.getLabel(), dto.getUsername(), dto.getPassword(), dto.getSyncPeriod());
		return new AccessAccountDTO(syncAccount);
	}

	@GET
	@Path("/username/{username}")
	@Produces("application/json")
	public AccessAccountDTO getAccessAccount(@PathParam String username) {
		AccessAccount syncAccount = service.getAccessAccount(username);
		return new AccessAccountDTO(syncAccount);
	}

	@DELETE
	@Path("/username/{username}")
	@Produces("application/json")
	public void deleteAccessAccount(@PathParam String username) {
		service.deleteAccessAccount(username);
	}
}
