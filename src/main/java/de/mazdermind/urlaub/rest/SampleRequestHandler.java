package de.mazdermind.urlaub.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class SampleRequestHandler
{
	@GET
	@Produces("text/plain")
	public String printMessage()
	{
		return "Foo";
	}
}
