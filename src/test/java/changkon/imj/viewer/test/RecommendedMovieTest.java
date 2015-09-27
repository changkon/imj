package changkon.imj.viewer.test;

import static org.junit.Assert.fail;

import java.util.HashSet;
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
import changkon.imj.dto.Movie;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.ViewerRecommendedMovies;
import changkon.imj.jaxb.JAXBMarshalPrint;
import changkon.imj.services.IMJApplication;

public class RecommendedMovieTest {

	private Logger logger = LoggerFactory.getLogger(RecommendedMovieTest.class);

	@Test
	public void testUpdateRecommended() {
		logger.info("Testing updating viewers recommended movies");

		Client client = ClientBuilder.newClient();

		try {
			// Create viewer
			Viewer viewer = new Viewer();
			viewer.setAge(15);
			viewer.setCountry("New Zealand");
			viewer.setFirstName("Sam");
			viewer.setLastName("Wells");
			viewer.setGender(Gender.MALE);

			// Add to database
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
			Response response = target.request().post(Entity.xml(viewer));

			String location = response.getLocation().toString();

			response.close();

			logger.info("URI for new viewer is: " + location);

			String[] split = location.split("/");

			long id = Long.parseLong(split[split.length-1]);

			Movie movie1 = new Movie();
			movie1.setTitle("Spirited Away");
			movie1.setDirector("Hayao Miyazaki");
			movie1.setCountry("Japan");
			movie1.setLanguage("Japanese");
			movie1.setRuntime(125);
			movie1.setGenre(Genre.ANIMATION);
			movie1.setRelease(new DateTime(2001, 6, 20, 0, 0));

			Movie movie2 = new Movie();
			movie2.setTitle("Laputa: Castle in the Sky");
			movie2.setDirector("Hayao Miyazaki");
			movie2.setCountry("Japan");
			movie2.setLanguage("Japanese");
			movie2.setRuntime(124);
			movie2.setRelease(new DateTime(1986, 8, 2, 0, 0));
			movie2.setGenre(Genre.ANIMATION);

			ViewerRecommendedMovies recommendedMovies = new ViewerRecommendedMovies();
			Set<Movie> recommendedMoviesSet = new HashSet<Movie>();
			recommendedMoviesSet.add(movie1);
			recommendedMoviesSet.add(movie2);
			recommendedMovies.setRecommendedMovies(recommendedMoviesSet);

			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/recommended").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(recommendedMovies));

			int status = response.getStatus();

			if (status != 204) {
				logger.error("Failed to update viewer's movie recommendations. Web service responded with status: " + status);
				fail();
			}

			logger.info("Printing movie recommendations");
			JAXBMarshalPrint.marshalPrint(recommendedMovies, ViewerRecommendedMovies.class, logger);

		} finally {
			client.close();
		}
	}

	@Test
	public void testRecommendedGet() {
		logger.info("Testing recommended movie query");

		Client client = ClientBuilder.newClient();

		try {
			// Create viewer
			Viewer viewer = new Viewer();
			viewer.setAge(10);
			viewer.setCountry("New Zealand");
			viewer.setFirstName("Alice");
			viewer.setLastName("Miles");
			viewer.setGender(Gender.FEMALE);

			// Add to database
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
			Response response = target.request().post(Entity.xml(viewer));

			String location = response.getLocation().toString();

			response.close();

			logger.info("URI for new viewer is: " + location);

			String[] split = location.split("/");

			long id = Long.parseLong(split[split.length-1]);

			Movie movie1 = new Movie();
			movie1.setTitle("Toy Story");
			movie1.setDirector("John Lasseter");
			movie1.setCountry("USA");
			movie1.setLanguage("English");
			movie1.setRuntime(81);
			movie1.setGenre(Genre.ANIMATION);
			movie1.setRelease(new DateTime(1995, 11, 22, 0, 0));

			Movie movie2 = new Movie();
			movie2.setTitle("The Lion King");
			movie2.setDirector("Roger Allers");
			movie2.setCountry("USA");
			movie2.setLanguage("English");
			movie2.setRuntime(89);
			movie2.setRelease(new DateTime(1994, 6, 24, 0, 0));
			movie2.setGenre(Genre.ANIMATION);

			ViewerRecommendedMovies recommendedMovies = new ViewerRecommendedMovies();
			Set<Movie> recommendedMoviesSet = new HashSet<Movie>();
			recommendedMoviesSet.add(movie1);
			recommendedMoviesSet.add(movie2);
			recommendedMovies.setRecommendedMovies(recommendedMoviesSet);

			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/recommended").resolveTemplate("id", id);
			response = target.request().put(Entity.xml(recommendedMovies));
			
			response.close();
			
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}/recommended").resolveTemplate("id", id);
			ViewerRecommendedMovies queryRecommendedMovies = target.request().get(ViewerRecommendedMovies.class);
			
			if (queryRecommendedMovies == null) {
				logger.error("Failed to query recommended movies");
				fail();
			}
			
			logger.info("Printing queried recommended movies");
			JAXBMarshalPrint.marshalPrint(queryRecommendedMovies, ViewerRecommendedMovies.class, logger);
			
		} finally {
			client.close();
		}
	}

}
