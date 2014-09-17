package com.pampanet.sample.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.google.inject.Inject;

/**
 * Sample Secured RESTful Web Service<br>
 * 
 * The constructor has Guice injections to enable also Shiro AOP annotations
 * 
 * @see com.pampanet.sample.servlet.modules.BootstrapServletModule
 * @see com.pampanet.sample.shiro.modules.ShiroAnnotationsModule
 * @author pablo.biagioli
 *
 */
@Path("/stuff")
public class SampleSecuredRESTWebService {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	
	@Inject
	public SampleSecuredRESTWebService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@GET
	@Path("/allowed")
	@RequiresPermissions("lightsaber:allowed")
	@RequiresAuthentication
	public Response sayHelloToUser(){
		return Response.ok("Hello "+SecurityUtils.getSubject().getPrincipal()+"!").build();
	}
	
	@GET
	@Path("/forbidden")
	@RequiresPermissions("forbiddenForAllExceptRoot")
	public Response forbiddenToAll(){
		return Response.ok("Got Root!").build();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
}