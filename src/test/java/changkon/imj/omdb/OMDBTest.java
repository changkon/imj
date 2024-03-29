package changkon.imj.omdb;

import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Movie;
import changkon.imj.jackson.JsonPrint;
import changkon.imj.services.IMJApplication;

public class OMDBTest {

	private Logger logger = LoggerFactory.getLogger(OMDBTest.class);
	
	// Movie used to test OMDB
	private long movieId;
	
	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient();
		
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setRelease(new DateTime(2010, 7, 16, 0, 0));
		movie.setLanguage("English");
		movie.setCountry("USA");
		movie.setRuntime(148);
		movie.setDirector("Christopher Nolan");
		movie.setGenre(Genre.ACTION);
		
		WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
		Response response = target.request().post(Entity.json(movie));
		
		String location = response.getLocation().toString();
		String[] split = location.split("/");
		movieId = Long.parseLong(split[split.length-1]);
		
		response.close();
		client.close();
	}
	
	@After
	public void tearDown() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}").resolveTemplate("id", movieId);
		Response response = target.request().delete();
		response.close();
		client.close();
	}
	
	@Test
	public void testMovieGet() {
		logger.info("Testing movie notification");
		final Client client = ClientBuilder.newClient();
		
		try {
			WebTarget target = client.target(IMJApplication.BASEURI + "/omdb/{id:\\d+}").resolveTemplate("id", movieId);
			
			Future<String> future = target.request().async().get(new InvocationCallback<String>() {
				
				public void completed(String message) {
					try {
						logger.info("Received callback message for OMDB api connection");
					} catch (Exception e) {
						
					}
				}
				
				public void failed(Throwable t) {
					t.printStackTrace();
				}
				
			});
			
			
			logger.info("Calling OMDB api");
			future.get();
			
		} catch (Exception e) {
			logger.error("Failed to contact OMDB api");
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
	
}
