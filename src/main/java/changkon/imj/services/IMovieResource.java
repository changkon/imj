package changkon.imj.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import changkon.imj.dto.Movie;

@Path("/movie")
public interface IMovieResource {

	@POST
	@Consumes("application/xml")
	public Movie createMovie(Movie movie);
	
	@GET
	@Produces("application/xml")
	public Movie queryMovieList();
	
	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}")
	public Movie queryMovie(@PathParam("id") int id);
	
	@PUT
	@Produces("application/xml")
	@Path("{id:\\d+}")
	public Movie updateMovie(@PathParam("id") int id);
}
