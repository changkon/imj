package changkon.imj.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/omdb")
public interface IOMDBResource {

	@GET
	@Produces("application/json")
	@Path("{id:\\d+}")
	public String queryMovie(@PathParam("id") long id);
}
