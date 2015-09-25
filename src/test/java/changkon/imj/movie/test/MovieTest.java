package changkon.imj.movie.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieCast;
import changkon.imj.dto.Movies;
import changkon.imj.jaxb.JAXBMarshalPrint;
import changkon.imj.services.IMJApplication;

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
	public void testMovieGetAll() {
		logger.info("Querying all movies");
		
		Client client = ClientBuilder.newClient();
		try {
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Movies movies = target.request().get(Movies.class);
			
			logger.info("Printing movies list");
			
			JAXBMarshalPrint.marshalPrint(movies, Movies.class, logger);
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
			
			String location = response.getLocation().toString();
			
			response.close();
			
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
			assertTrue(movie.equals(queryMovie));
			
			logger.info("Queried movie is equal to movie created earlier");
			
			logger.info("Printing queried movie");
			JAXBMarshalPrint.marshalPrint(queryMovie, Movie.class, logger);
			
		} finally {
			client.close();
		}
		
	}
	
	@Test
	public void testMovieUpdate() {
		logger.info("Updating movie");
		
		Client client = ClientBuilder.newClient();
		try {
			// Have to create movie first. Has incorrect information
			Movie movie = new Movie();
			movie.setTitle("Citizen Kane");
			movie.setDirector("Orson Welles");
			movie.setCountry("USA");
			movie.setLanguage("French");
			movie.setRuntime(60);
			movie.setGenre(Genre.ACTION);
			movie.setRelease(new DateTime(1941, 9, 5, 0, 0));
			
			logger.info("Print incorrect movie");
			JAXBMarshalPrint.marshalPrint(movie, Movie.class, logger);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			logger.info("Updating created movie details");
			Movie correctMovie = new Movie();
			correctMovie.setTitle("Citizen Kane");
			correctMovie.setDirector("Orson Welles");
			correctMovie.setCountry("USA");
			correctMovie.setLanguage("English");
			correctMovie.setRuntime(119);
			correctMovie.setGenre(Genre.DRAMAFILM);
			correctMovie.setRelease(new DateTime(1941, 9, 5, 0, 0));
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(correctMovie));
			response.close();
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}").resolveTemplate("id", id);
			
			Movie queryMovie = target.request().get(Movie.class);
			response.close();
			// Check that updated values are correct
			assertTrue(correctMovie.equals(queryMovie));
			
			logger.info("Movie has been updated successfully");
			
			logger.info("Printing updated movie");
			JAXBMarshalPrint.marshalPrint(queryMovie, Movie.class, logger);
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMovieCastUpdate() {
		logger.info("Updating movie cast");
		
		Client client = ClientBuilder.newClient();
		
		try {
			// Create movie first
			Movie movie = new Movie();
			movie.setTitle("Birdman");
			movie.setDirector("Alejandro G. Inarritu");
			movie.setCountry("USA");
			movie.setGenre(Genre.DRAMAFILM);
			movie.setLanguage("English");
			movie.setRelease(new DateTime(2014, 10, 17, 0, 0));
			movie.setRuntime(119);
			
			logger.info("Printing created movie");
			JAXBMarshalPrint.marshalPrint(movie, Movie.class, logger);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			Collection<String> cast = new ArrayList<String>();
			cast.add("Michael Keaton");
			cast.add("Emma Stone");
			cast.add("Zach Galifianakis");
			cast.add("Naomi Watts");
			
			MovieCast movieCast = new MovieCast();
			movieCast.setCast(cast);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/cast").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(movieCast));
			response.close();

			int status = response.getStatus();
			
			if (status != 204) {
				logger.error("Failed to update movie cast. Returned with response code: " + status);
			}
			
			// Should be 204 No Content
			logger.info("Movie cast successfully updated");
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMovieCastGet() {
		logger.info("Retrieving movie cast");
		
		Client client = ClientBuilder.newClient();
		
		try {
			// Create movie first
			Movie movie = new Movie();
			movie.setTitle("Interstellar");
			movie.setDirector("Christopher Nolan");
			movie.setCountry("USA");
			movie.setGenre(Genre.ADVENTURE);
			movie.setLanguage("English");
			movie.setRelease(new DateTime(2014, 11, 7, 0, 0));
			movie.setRuntime(169);
			
			logger.info("Printing created movie");
			JAXBMarshalPrint.marshalPrint(movie, Movie.class, logger);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			Collection<String> cast = new ArrayList<String>();
			cast.add("Matthew McConaughey");
			cast.add("Anne Hathaway");
			cast.add("Matt Damon");
			cast.add("Jessica Chastain");
			cast.add("Michael Caine");
			
			MovieCast movieCast = new MovieCast();
			movieCast.setCast(cast);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/cast").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(movieCast));
			response.close();
			
			logger.info("Query movie cast");
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/cast").resolveTemplate("id", id);
			MovieCast retrieveMovieCast = target.request().get(MovieCast.class);

			if (retrieveMovieCast == null) {
				logger.error("Failed to query movie cast from database. Web service responded with: 204");
				fail();
			}
			
			Collection<String> retrieveMovieCastCollection = retrieveMovieCast.getCast();
			
			assertTrue(retrieveMovieCastCollection.contains("Matthew McConaughey"));
			assertTrue(retrieveMovieCastCollection.contains("Anne Hathaway"));
			assertTrue(retrieveMovieCastCollection.contains("Matt Damon"));
			assertTrue(retrieveMovieCastCollection.contains("Jessica Chastain"));
			assertTrue(retrieveMovieCastCollection.contains("Michael Caine"));
			
			logger.info("Printing movie cast");
			JAXBMarshalPrint.marshalPrint(retrieveMovieCast, MovieCast.class, logger);
			
		} finally {
			client.close();
		}
		
	}
	
	@Test
	public void testMovieDescriptionGet() {
		
	}
	
	@Test
	public void testMovieDescriptionUpdate() {
		
	}
}
