package changkon.imj.movie.test;

import static org.junit.Assert.fail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Movie;

public class MovieTest {

	private Logger logger = LoggerFactory.getLogger(MovieTest.class);
	
	@Test
	public void testMovieCreate() {
		logger.info("Creating a movie instance");
		
		Client client = ClientBuilder.newClient();
		try {
			Movie movie = new Movie();
			movie.setTitle("The Dark Knight");
			movie.setDirector("Christopher Nolan");
			movie.setGenre(Genre.ACTION);
			movie.setLanguage("English");
			movie.setRelease(new DateTime(2008, 07, 24, 0, 0));
			movie.setCountry("USA");
			movie.setRuntime(152);
			Response response = client.target("http://localhost:10003/services/movie").request().post(Entity.xml(movie));
			int status = response.getStatus();
			
			if (status != 201) {
				logger.error("Failed to create movie. Web service responsed with: " + status);
				fail();
			}
			
			response.close();
			
		} finally {
			client.close();
		}
	}
	
}
