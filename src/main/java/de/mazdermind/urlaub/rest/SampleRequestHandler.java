package de.mazdermind.urlaub.rest;

import javax.ws.rs.Path;

public class SampleRequestHandler
{
	@Path("/test")
	public String printMessage()
	{
		return "Foo";
	}
}
