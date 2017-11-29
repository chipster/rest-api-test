package fi.csc.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fi.csc.rest.model.Thing;

@Path("")
public class ThingResource {

	private Thing thing;

	public ThingResource(Thing thing) {
		this.thing = thing;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(thing).build();
	}
}
