package changkon.imj.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import changkon.imj.dto.Movie;
import changkon.imj.dto.Movies;

@Path("/movie")
public interface IMovieResource {

	/**
	 * <p>Creates a new movie object and places it into database</p>
	 * <p>Should return a Created 201 message indicating URI of created object</p>
	 * @param movie
	 * @return URI of created movie
	 */
	
	@POST
	@Consumes("application/xml")
	public Response createMovie(Movie movie);
	
	@GET
	@Produces("application/xml")
	public Movies queryMovieList();
	
	/**
	 * Returns XML of movie
	 * @param id
	 * @return 200 "OK" HTTP if Movie isn't null else 204 "No Content"
	 */
	
	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}")
	public Movie queryMovie(@PathParam("id") long id);
	
	/**
	 * Updates the movie specified in id with new DTO movie
	 * @param id
	 * @return 204 No Content
	 */
	
	@PUT
	@Produces("application/xml")
	@Path("{id:\\d+}")
	public void updateMovie(@PathParam("id") long id, Movie movie);
}
