package changkon.imj.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieCast;
import changkon.imj.dto.MovieDescription;
import changkon.imj.dto.MoviePoster;
import changkon.imj.dto.MovieReleaseDates;
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
	@Consumes("application/xml")
	@Path("{id:\\d+}")
	public void updateMovie(@PathParam("id") long id, Movie movie);

	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}/cast")
	public MovieCast queryCast(@PathParam("id") long id);
	
	@PUT
	@Consumes("application/xml")
	@Path("{id:\\d+}/cast")
	public void updateCast(@PathParam("id") long id, MovieCast cast);
	
	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}/description")
	public MovieDescription queryDescription(@PathParam("id") long id);

	@PUT
	@Consumes("application/xml")
	@Path("{id:\\d+}/description")
	public void updateDescription(@PathParam("id") long id, MovieDescription description);
	
	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}/release")
	public MovieReleaseDates queryReleaseDates(@PathParam("id") long id);
	
	@PUT
	@Consumes("application/xml")
	@Path("{id:\\d+}/release")
	public void updateReleaseDates(@PathParam("id") long id, MovieReleaseDates movieReleaseDates);
	
	@GET
	@Produces("application/xml")
	@Path("{id:\\d+}/poster")
	public MoviePoster queryPoster(@PathParam("id") long id);
	
	@PUT
	@Consumes("application/xml")
	@Path("{id:\\d+}/poster")
	public void updatePoster(@PathParam("id") long id, MoviePoster poster);

}
