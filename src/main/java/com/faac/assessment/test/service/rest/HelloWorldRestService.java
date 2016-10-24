package com.faac.assessment.test.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
 
public class HelloWorldRestService
{
	@GET
	@Path("/print")
	@Produces("text/plain")
	public Response getStartingPage()
	{
		// String output = "<h1>Hello World!<h1>" +
		// "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
		return Response.ok().entity("HelloWorld").build();
	}
}
