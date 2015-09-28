package changkon.imj.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("/omdb")
public interface IOMDBResource {

	@GET
	@Produces("application/json")
	@Path("{id:\\d+}")
	public void queryMovie(@PathParam("id") final long id, final @Suspended AsyncResponse response);
	
}
