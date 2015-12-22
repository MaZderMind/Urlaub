package de.mazdermind.urlaub.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/test")
public class SampleRequestHandler
{
	@GET
	@Produces("text/html")
	public String getAsHtml()
	{
		return "<h1>Foo/h1>";
	}

	@GET
	@Produces("text/plain")
	public String getAsText()
	{
		return "Foo";
	}
}
