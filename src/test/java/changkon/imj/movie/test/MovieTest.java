package changkon.imj.movie.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Movie;
import changkon.imj.services.IMJApplication;
import junit.framework.Assert;

public class MovieTest {

	private Logger logger = LoggerFactory.getLogger(MovieTest.class);
	
	/**
	 * Tests ability for movie to be created and persisted into database
	 */
	
	@Test
	public void testMovieCreate() {
		logger.info("Creating a movie instance");
		
		Client client = ClientBuilder.newClient();
		try {
			// Create dark knight movie
			Movie movie = new Movie();
			movie.setTitle("The Dark Knight");
			movie.setDirector("Christopher Nolan");
			movie.setGenre(Genre.ACTION);
			movie.setLanguage("English");
			movie.setRelease(new DateTime(2008, 07, 24, 0, 0));
			movie.setCountry("USA");
			movie.setRuntime(152);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			
			Response response = target.request().post(Entity.xml(movie));
			int status = response.getStatus();
			
			if (status != 201) {
				logger.error("Failed to create movie. Web service responsed with: " + status);
				fail();
			}
			
			String location = response.getLocation().toString();
			
			logger.info("URI for new movie is: " + location);
			response.close();
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMovieGet() {
		logger.info("Querying movie");
		
		Client client = ClientBuilder.newClient();
		try {
			// Have to create movie first to ensure movie is in database
			// Create The Social Network
			Movie movie = new Movie();
			movie.setTitle("The Social Network");
			movie.setDirector("David Fincher");
			movie.setGenre(Genre.DRAMAFILM);
			movie.setLanguage("English");
			movie.setRelease(new DateTime(2010, 11, 11, 0, 0));
			movie.setCountry("USA");
			movie.setRuntime(120);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			int status = response.getStatus();
			
			if (status != 201) {
				logger.error("Failed to create movie. Web service responded with: " + status);
				fail();
			}
			
			response.close();
			
			String location = response.getLocation().toString();
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			logger.info("Querying created movie");
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}").resolveTemplate("id", id);
			
			Movie queryMovie = target.request().get(Movie.class);
			
			if (queryMovie == null) {
				logger.error("Failed to query movie from database. Web service responded with: 204");
				fail();
			}
			
			// Compare movie
			assertEquals(movie.getTitle(), queryMovie.getTitle());
			assertEquals(movie.getDirector(), queryMovie.getDirector());
			assertEquals(movie.getGenre(), queryMovie.getGenre());
			assertEquals(movie.getLanguage(), queryMovie.getLanguage());
			assertTrue(movie.getRelease().toLocalDate().isEqual(queryMovie.getRelease().toLocalDate()));
			assertEquals(movie.getCountry(), queryMovie.getCountry());
			assertEquals(movie.getRuntime(), queryMovie.getRuntime());
			
			logger.info("Queried movie is equal to movie created earlier");
			
		} finally {
			client.close();
		}
		
	}
	
}
