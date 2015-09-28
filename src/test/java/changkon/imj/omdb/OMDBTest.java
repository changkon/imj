package changkon.imj.omdb;

import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Movie;
import changkon.imj.services.IMJApplication;

public class OMDBTest {

	private Logger logger = LoggerFactory.getLogger(OMDBTest.class);
	
	@Test
	public void testMovieGet() {
		logger.info("Testing movie notification");
		final Client client = ClientBuilder.newClient();
		
		try {
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
			
			response.close();
			
			Future<String> future = target.request().async().get(new InvocationCallback<String>() {
				
				public void completed(String message) {
					logger.info("Received callback message for OMDB api connection");
					logger.info(message);
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
