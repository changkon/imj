package changkon.imj.viewer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Gender;
import changkon.imj.domain.Genre;
import changkon.imj.domain.GeoLocation;
import changkon.imj.dto.Log;
import changkon.imj.dto.Movie;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.ViewerLogs;
import changkon.imj.jackson.JsonPrint;
import changkon.imj.jaxb.JAXB;
import changkon.imj.services.IMJApplication;

public class LogTest {

	private Logger logger = LoggerFactory.getLogger(LogTest.class);
	
	@Test
	public void testLogCreate() {
		logger.info("Creating log");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Viewer viewer = new Viewer();
			viewer.setAge(20);
			viewer.setCountry("South Korea");
			viewer.setFirstName("Chang Kon");
			viewer.setLastName("Han");
			viewer.setGender(Gender.MALE);
			
			Movie movie = new Movie();
			movie.setTitle("Maze Runner");
			movie.setDirector("Wes Ball");
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(113);
			movie.setGenre(Genre.ACTION);
			movie.setRelease(new DateTime(2014, 10, 10, 0, 0));
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
			Response response = target.request().post(Entity.xml(viewer));
			
			String location = response.getLocation().toString();
			
			logger.info("URI for new viewer is: " + location);
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			
			response.close();
			
			target = client.target(IMJApplication.BASEURI + "/movie");
			response = target.request().post(Entity.xml(movie));
			response.close();
			
			Log log = new Log();
			log.setDate(new DateTime(2015, 9, 26, 0, 0));
			log.setGeoLocation(new GeoLocation(51.5033630, -0.132885));
			log.setMovie(movie);
			log.setViewer(viewer);
			
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/log").resolveTemplate("id", id);
			response = target.request().post(Entity.xml(log));
			
			int status = response.getStatus();

			if (status != 201) {
				logger.error("Error creating log entry. Web Service responded with status: " + status);
				fail();
			}
			
			response.close();
			
			response = target.request().post(Entity.json(log));
			
			status = response.getStatus();

			if (status != 201) {
				logger.error("Error creating log entry. Web Service responded with status: " + status);
				fail();
			}
			
			response.close();
			
		} finally {
			client.close();
		}
		
	}

	@Test
	public void testLogQuery() {
		logger.info("Testing log query for specific user");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Viewer viewer = new Viewer();
			viewer.setAge(30);
			viewer.setCountry("USA");
			viewer.setFirstName("Alex");
			viewer.setLastName("Hamilton");
			viewer.setGender(Gender.MALE);
			
			// Add viewer to database
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
			Response response = target.request().post(Entity.xml(viewer));
			
			String location = response.getLocation().toString();
			
			logger.info("URI for new viewer is: " + location);
			
			response.close();
			
			String[] split = location.split("/");
			
			long id = Long.parseLong(split[split.length-1]);
			
			// Create some movies for log entries
			Movie movie1 = new Movie();
			movie1.setTitle("The Hunger Games");
			movie1.setDirector("Gary Ross");
			movie1.setLanguage("English");
			movie1.setRuntime(142);
			movie1.setRelease(new DateTime(2012, 3, 23, 0, 0));
			movie1.setGenre(Genre.ADVENTURE);
			movie1.setCountry("USA");
			
			Movie movie2 = new Movie();
			movie2.setTitle("The Hunger Games: Catching Fire");
			movie2.setDirector("Francis Lawrence");
			movie2.setLanguage("English");
			movie2.setRuntime(146);
			movie2.setRelease(new DateTime(2013, 11, 21, 0, 0));
			movie2.setGenre(Genre.ADVENTURE);
			movie2.setCountry("USA");
			
			// Create logs for viewer
			Log log1 = new Log();
			log1.setDate(new DateTime(2015, 9, 25, 0, 0));
			log1.setViewer(viewer);
			log1.setMovie(movie1);
			log1.setGeoLocation(new GeoLocation(-36.852049, 174.767817));
			
			Log log2 = new Log();
			log2.setDate(new DateTime(2015, 9, 26, 0, 0));
			log2.setViewer(viewer);
			log2.setMovie(movie2);
			log2.setGeoLocation(new GeoLocation(-36.852049, 174.767817));
			
			// Add to database
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/log").resolveTemplate("id", id);
			response = target.request().post(Entity.xml(log1));
			
			response.close();
			
			response = target.request().post(Entity.xml(log2));
			response.close();
			
			logger.info("Querying logs for user");
			// Query logs for user
			ViewerLogs viewerLogs = target.request().get(ViewerLogs.class);
			response.close();
			
			if (viewerLogs == null) {
				logger.error("Failed to query viewer logs");
				fail();
			}
			
		} finally {
			client.close();
		}
	}
}
