package changkon.imj.movie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import changkon.imj.dto.MovieCast;
import changkon.imj.dto.MovieDescription;
import changkon.imj.dto.MoviePoster;
import changkon.imj.dto.MovieReleaseDates;
import changkon.imj.dto.Movies;
import changkon.imj.jackson.JsonPrint;
import changkon.imj.jaxb.JAXB;
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
			
			logger.info("Inputting xml");
			Response response = target.request().post(Entity.xml(movie));
			int status = response.getStatus();
			
			if (status != 201) {
				logger.error("Failed to create movie with xml. Web service responsed with: " + status);
				fail();
			}
			
			String location = response.getLocation().toString();
			
			logger.info("URI for new movie is: " + location);
			response.close();
			
			logger.info("Inputting json");

			response = target.request().post(Entity.json(movie));
			status = response.getStatus();
			
			if (status != 201) {
				logger.error("failed to create movie with json. Web service responded with: " + status);
				fail();
			}
			
			location = response.getLocation().toString();
			
			logger.info("URI for new movie is: " + location);
			response.close();
			
			logger.info("Completed creating movie");
			
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
			target.request().get(Movies.class);
			
			//logger.info("Printing movies list");
			
			//JAXB.prettyPrint(movies, Movies.class, logger);
			//JsonPrint.prettyPrint(movies, logger);
			
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
			JAXB.prettyPrint(queryMovie, Movie.class, logger);
			
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
			JAXB.prettyPrint(movie, Movie.class, logger);
			
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
			JAXB.prettyPrint(queryMovie, Movie.class, logger);
			
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
			JAXB.prettyPrint(movie, Movie.class, logger);
			
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

			int status = response.getStatus();
			
			response.close();
			if (status != 204) {
				logger.error("Failed to update movie cast xml. Returned with response code: " + status);
				fail();
			}
			
			response = target.request().put(Entity.json(movieCast));
			
			status = response.getStatus();
			response.close();
			
			if (status != 204) {
				logger.error("Failed to update movie cast with json. Returned with response code: " + status);
				fail();
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
			JAXB.prettyPrint(movie, Movie.class, logger);
			
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
			JAXB.prettyPrint(retrieveMovieCast, MovieCast.class, logger);
			
		} finally {
			client.close();
		}
		
	}
	
	@Test
	public void testMovieDescriptionGet() {
		logger.info("Retrieving movie description");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("Chris Buck");
			movie.setTitle("Frozen");
			movie.setGenre(Genre.ANIMATION);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(102);
			movie.setRelease(new DateTime(2013, 12, 6, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MovieDescription description = new MovieDescription();
			
			String movieDescription = "When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.";
			
			description.setDescription(movieDescription);
			
			logger.info("Adding movie description to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/description").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(description));
			response.close();
			logger.info("Querying movie description");
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/description").resolveTemplate("id", id);
			MovieDescription queryDescription = target.request().get(MovieDescription.class);
			
			assertEquals(movieDescription, queryDescription.getDescription());
			
			logger.info("Retrieved movie description matches");
			logger.info("Printing movie description for movie id: " + id);
			
			JAXB.prettyPrint(queryDescription, MovieDescription.class, logger);
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMovieDescriptionUpdate() {
		logger.info("Updating movie description");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("Martin Scorsese");
			movie.setTitle("The Wolf of Wall Street");
			movie.setGenre(Genre.BIOGRAPHY);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(180);
			movie.setRelease(new DateTime(2014, 1, 17, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MovieDescription description = new MovieDescription();
			
			String movieDescription = "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.";
			
			description.setDescription(movieDescription);
			
			logger.info("Adding movie description to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/description").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(description));
			
			int status = response.getStatus();
			
			response.close();
			
			if (status != 204) {
				logger.error("Failed to update movie description with xml. Returned with response code: " + status);
				fail();
			}
			
			response = target.request().put(Entity.json(description));
			
			status = response.getStatus();
			
			if (status != 204) {
				logger.error("Failed to update movie description with json. Returned with response code: " + status);
				fail();
			}
			
			JsonPrint.prettyPrint(description, logger);
			
		} finally {
			client.close();
		}
	}

	@Test
	public void testMovieReleaseDatesGet() {
		logger.info("Retrieving movie release dates");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("David Fincher");
			movie.setTitle("Gone Girl");
			movie.setGenre(Genre.THRILLER);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(149);
			movie.setRelease(new DateTime(2014, 10, 3, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MovieReleaseDates releaseDates = new MovieReleaseDates();
			
			Map<String, DateTime> releaseDateMap = new HashMap<String, DateTime>();
			
			releaseDateMap.put("USA", new DateTime(2014, 10, 3, 0, 0));
			releaseDateMap.put("UK", new DateTime(2014, 10, 2, 0, 0));
			releaseDateMap.put("New Zealand", new DateTime(2014, 10, 2, 0, 0));
			releaseDateMap.put("South Korea", new DateTime(2014, 10, 23, 0, 0));
			releaseDateMap.put("Japan", new DateTime(2014, 12, 11, 0, 0));
			
			releaseDates.setReleases(releaseDateMap);
			
			logger.info("Adding movie release dates to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/release").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(releaseDates));
			response.close();
			logger.info("Querying movie release dates");
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/release").resolveTemplate("id", id);
			MovieReleaseDates queryReleaseDates = target.request().get(MovieReleaseDates.class);
			Map<String, DateTime> queryReleaseDateMap = queryReleaseDates.getReleases();
			
			assertTrue(queryReleaseDateMap.get("USA").toLocalDate().isEqual(releaseDateMap.get("USA").toLocalDate()));
			assertTrue(queryReleaseDateMap.get("UK").toLocalDate().isEqual(releaseDateMap.get("UK").toLocalDate()));
			assertTrue(queryReleaseDateMap.get("New Zealand").toLocalDate().isEqual(releaseDateMap.get("New Zealand").toLocalDate()));
			assertTrue(queryReleaseDateMap.get("South Korea").toLocalDate().isEqual(releaseDateMap.get("South Korea").toLocalDate()));
			assertTrue(queryReleaseDateMap.get("Japan").toLocalDate().isEqual(releaseDateMap.get("Japan").toLocalDate()));
			
			logger.info("Retrieved movie release dates matches");
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMovieReleaseDatesUpdate() {
		logger.info("Update movie release dates");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("David Fincher");
			movie.setTitle("The Girl with the Dragon Tattoo");
			movie.setGenre(Genre.CRIME);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(158);
			movie.setRelease(new DateTime(2011, 12, 20, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MovieReleaseDates releaseDates = new MovieReleaseDates();
			
			Map<String, DateTime> releaseDateMap = new HashMap<String, DateTime>();
			
			releaseDateMap.put("USA", new DateTime(2011, 12, 20, 0, 0));
			releaseDateMap.put("UK", new DateTime(2011, 12, 26, 0, 0));
			releaseDateMap.put("Canada", new DateTime(2011, 12, 20, 0, 0));
			releaseDateMap.put("France", new DateTime(2012, 1, 18, 0, 0));
			
			releaseDates.setReleases(releaseDateMap);
			
			logger.info("Adding movie release dates to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/release").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(releaseDates));
			int status = response.getStatus();
			response.close();

			if (status != 204) {
				logger.error("Failed to update movie release dates with xml. Returned with response code: " + status);
			}
			
			response = target.request().put(Entity.json(releaseDates));
			status = response.getStatus();
			response.close();

			if (status != 204) {
				logger.error("Failed to update movie release dates with json. Returned with response code: " + status);
			}
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMoviePosterGet() {
		logger.info("Retrieving movie poster");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("Jonathan Demme");
			movie.setTitle("The Silence of the Lambs");
			movie.setGenre(Genre.CRIME);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(118);
			movie.setRelease(new DateTime(1991, 5, 31, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MoviePoster poster = new MoviePoster();
			
			String url = "http://ia.media-imdb.com/images/M/MV5BMTQ2NzkzMDI4OF5BMl5BanBnXkFtZTcwMDA0NzE1NA@@._V1_SX214_AL_.jpg";
			
			poster.setUrl(url);
			
			logger.info("Adding movie poster to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/poster").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(poster));
			response.close();
			
			logger.info("Querying movie poster");
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/poster").resolveTemplate("id", id);
			MoviePoster queryMoviePoster = target.request().get(MoviePoster.class);

			assertEquals(url, queryMoviePoster.getUrl());
			
			logger.info("Retrieved movie poster matches");
			logger.info("Printing movie release dates for movie id: " + id);
			
			JAXB.prettyPrint(queryMoviePoster, MoviePoster.class, logger);
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testMoviePosterUpdate() {
		logger.info("Updating movie poster");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setDirector("David Fincher");
			movie.setTitle("Se7en");
			movie.setGenre(Genre.DRAMAFILM);
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(127);
			movie.setRelease(new DateTime(1996, 1, 5, 0, 0));
			
			logger.info("Putting movie into database");
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			String location = response.getLocation().toString();
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			logger.info("Created movie id is: " + id);
			
			MoviePoster poster = new MoviePoster();
			
			String url = "http://ia.media-imdb.com/images/M/MV5BMTQwNTU3MTE4NF5BMl5BanBnXkFtZTcwOTgxNDM2Mg@@._V1_SX214_AL_.jpg";
			
			poster.setUrl(url);
			
			logger.info("Adding movie poster to movie id: " + id);
			
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}/poster").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(poster));
			int status = response.getStatus();
			response.close();
			
			if (status != 204) {
				logger.error("Failed to update movie poster with xml. Returned with response code: " + status);
			}
			
			response = target.request().put(Entity.json(poster));
			status = response.getStatus();
			response.close();
			
			if (status != 204) {
				logger.error("Failed to update movie poster with json. Returned with response code: " + status);
			}
			
		} finally {
			client.close();
		}
	}

}
