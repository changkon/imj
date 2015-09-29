package changkon.imj.viewer;

import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
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
import changkon.imj.services.IMJApplication;

public class LogTest {

	private Logger logger = LoggerFactory.getLogger(LogTest.class);
	
	private Viewer viewer;
	
	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient();
		
		Viewer viewer = new Viewer();
		viewer.setCountry("South Korea");
		viewer.setFirstName("Chang Kon");
		viewer.setLastName("Han");
		viewer.setGender(Gender.MALE);
		
		WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
		Response response = target.request().post(Entity.xml(viewer));
		
		String location = response.getLocation().toString();
		logger.info("URI for new viewer is: " + location);
		
		String[] split = location.split("/");
		
		long id = Long.parseLong(split[split.length-1]);
		
		response.close();
		
		target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", id);
		
		this.viewer = target.request().get(Viewer.class);
		
		response.close();
		
		client.close();
	}
	
	@After
	public void tearDown() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", viewer.getId());
		Response response = target.request().delete();
		response.close();
		client.close();
	}
	
	@Test
	public void testLogCreate() {
		logger.info("Creating log");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Movie movie = new Movie();
			movie.setTitle("Maze Runner");
			movie.setDirector("Wes Ball");
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(113);
			movie.setGenre(Genre.ACTION);
			movie.setRelease(new DateTime(2014, 10, 10, 0, 0));
			
			Log log = new Log();
			log.setDate(new DateTime(2015, 9, 26, 0, 0));
			log.setGeoLocation(new GeoLocation(51.5033630, -0.132885));
			log.setMovie(movie);
			log.setViewer(viewer);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/log").resolveTemplate("id", viewer.getId());
			
			// Post with xml
			Response response = target.request().post(Entity.xml(log));
			
			int status = response.getStatus();

			if (status != 201) {
				logger.error("Error creating log entry with xml. Web Service responded with status: " + status);
				fail();
			}
			
			response.close();
			
			// Post with json
			response = target.request().post(Entity.json(log));
			status = response.getStatus();

			if (status != 201) {
				logger.error("Error creating log entry with json. Web Service responded with status: " + status);
				fail();
			}
			
			response.close();
			logger.info("Created log successfully");
			
		} finally {
			client.close();
		}
		
	}

	@Test
	public void testLogQuery() {
		logger.info("Testing log query for specific user");
		
		Client client = ClientBuilder.newClient();
		
		try {			
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
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/log").resolveTemplate("id", viewer.getId());
			
			// Post log with xml
			Response response = target.request().post(Entity.xml(log1));
			
			response.close();
			
			// Post log with json
			response = target.request().post(Entity.json(log2));
			response.close();
			
			logger.info("Querying logs for user");
			
			// Query logs for user
			ViewerLogs viewerLogs = target.request().get(ViewerLogs.class);
			response.close();
			
			if (viewerLogs == null) {
				logger.error("Failed to query viewer logs");
				fail();
			}
			
			logger.info("Retrieved viewer logs successfully");
		} finally {
			client.close();
		}
	}
}
