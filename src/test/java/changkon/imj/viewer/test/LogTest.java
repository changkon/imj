package changkon.imj.viewer.test;

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

import changkon.imj.domain.Gender;
import changkon.imj.domain.Genre;
import changkon.imj.domain.GeoLocation;
import changkon.imj.dto.Log;
import changkon.imj.dto.Movie;
import changkon.imj.dto.Viewer;
import changkon.imj.jaxb.JAXBMarshalPrint;
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
			
			logger.info("Printing viewer");
			JAXBMarshalPrint.marshalPrint(viewer, Viewer.class, logger);
			
			Movie movie = new Movie();
			movie.setTitle("Maze Runner");
			movie.setDirector("Wes Ball");
			movie.setCountry("USA");
			movie.setLanguage("English");
			movie.setRuntime(113);
			movie.setGenre(Genre.ACTION);
			movie.setRelease(new DateTime(2014, 10, 10, 0, 0));
			
			logger.info("Printing movie");
			JAXBMarshalPrint.marshalPrint(movie, Movie.class, logger);
			
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
			
			JAXBMarshalPrint.marshalPrint(log, Log.class, logger);
			
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/log").resolveTemplate("id", id);
			response = target.request().post(Entity.xml(log));
			
			int status = response.getStatus();

			if (status != 200) {
				logger.error("Error creating log entry. Web Service responded with status: " + status);
				fail();
			}
			
			response.close();
			
		} finally {
			client.close();
		}
		
	}
	
}
