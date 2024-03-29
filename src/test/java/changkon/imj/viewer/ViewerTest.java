package changkon.imj.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
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
import changkon.imj.dto.Movie;
import changkon.imj.dto.Viewer;
import changkon.imj.services.IMJApplication;

public class ViewerTest {

	private Logger logger = LoggerFactory.getLogger(ViewerTest.class);
	
	private Viewer viewer;
	
	private boolean createdViewer;
	private long createdViewerId;
	
	private boolean createdMovie;
	private long createdMovieId;
	
	@Before
	public void setUp() {
		// initialize false
		createdViewer = false;
		createdMovie = false;
		
		Client client = ClientBuilder.newClient();
		
		Viewer viewer = new Viewer();
		viewer.setAge(19);
		viewer.setCountry("New Zealand");
		viewer.setFirstName("Luke");
		viewer.setLastName("Holly");
		viewer.setGender(Gender.MALE);
		
		WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
		Response response = target.request().post(Entity.xml(viewer));
		
		String location = response.getLocation().toString();
		String[] split = location.split("/");
		long id = Long.parseLong(split[split.length-1]);
		
		logger.info("Created viewer id is: " + id);
		
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
		
		if (createdViewer) {
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", createdViewerId);
			response = target.request().delete();
			response.close();
		}
		
		if (createdMovie) {
			target = client.target(IMJApplication.BASEURI + "/movie/{id:\\d+}").resolveTemplate("id", createdMovieId);
			response = target.request().delete();
			response.close();
		}
		
		client.close();
	}
	
	@Test
	public void testViewerCreate() {
		logger.info("Creating a viewer");
		Client client = ClientBuilder.newClient();
		
		try {
			Viewer viewer = new Viewer();
			
			viewer.setAge(19);
			viewer.setCountry("New Zealand");
			viewer.setFirstName("Luke");
			viewer.setLastName("Holly");
			viewer.setGender(Gender.MALE);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer");
			
			Response response = target.request().post(Entity.xml(viewer));
			
			int status = response.getStatus();
			
			if (status != 201) {
				logger.error("Failed to create viewer. Web service responded with: " + status);
				fail();
			}
			
			String location = response.getLocation().toString();
			String[] split = location.split("/");
			createdViewerId = Long.parseLong(split[split.length-1]);
			createdViewer = true;
			logger.info("URI for new viewer is: " + location);
			
			response.close();
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testViewerDelete() {
		logger.info("Deleting viewer");
		
		Client client = ClientBuilder.newClient();
		
		try {	
			// Delete viewer
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", viewer.getId());
			
			Response response = target.request().delete();
			
			int status = response.getStatus();
			
			if (status != 204) {
				logger.error("Failed to delete viewer");
			}
			
			logger.info("Successfully deleted viewer");
			
		} finally {
			client.close();
		}
	}
	
	@Test
	public void testViewerUpdate() {
		logger.info("Update viewer test");
		
		Client client = ClientBuilder.newClient();
		
		try {
			Viewer updateViewer = new Viewer();
			updateViewer.setAge(20);
			updateViewer.setFirstName("Jennifer");
			updateViewer.setLastName("Lu");
			updateViewer.setCountry("China");
			updateViewer.setGender(Gender.FEMALE);
			
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", viewer.getId());
			
			Response response = target.request().put(Entity.xml(updateViewer));
			
			int status = response.getStatus();
			
			response.close();
			
			if (status != 204) {
				logger.error("Failed to update viewer. Returned with response code: " + status);
				fail();
			}
			
			// Check that update is successful
			target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", viewer.getId());
			Viewer queryViewer = target.request().get(Viewer.class);
			
			assertEquals(updateViewer.getAge(), queryViewer.getAge());
			assertEquals(updateViewer.getFirstName(), queryViewer.getFirstName());
			assertEquals(updateViewer.getLastName(), queryViewer.getLastName());
			assertEquals(updateViewer.getCountry(), queryViewer.getCountry());
			assertEquals(updateViewer.getGender(), queryViewer.getGender());
			
			logger.info("Updated viewer successfully");
			
		} finally {
			client.close();
		}
	}

	@Test
	public void testViewerGet() {
		
		logger.info("Retrieving a viewer");
		
		Client client = ClientBuilder.newClient();
		
		try {
			WebTarget target = client.target(IMJApplication.BASEURI + "/viewer/{id:\\d+}").resolveTemplate("id", viewer.getId());
			Viewer queryViewer = target.request().get(Viewer.class);
			
			assertEquals(viewer.getAge(), queryViewer.getAge());
			assertEquals(viewer.getFirstName(), queryViewer.getFirstName());
			assertEquals(viewer.getLastName(), queryViewer.getLastName());
			assertEquals(viewer.getCountry(), queryViewer.getCountry());
			assertEquals(viewer.getGender(), queryViewer.getGender());
			
			logger.info("Retrieved viewer successfully");
			
		} finally {
			client.close();
		}
		
	}

	@Test
	public void testMovieNotification() {
		
		logger.info("Testing movie notification");
		final Client client = ClientBuilder.newClient();
		
		try {		
			int timeDifference = 10;
			
			// Create mock movie
			DateTime now = DateTime.now();
			DateTime releaseDate = now.plusSeconds(timeDifference);
			
			Movie movie = new Movie();
			movie.setTitle("Mock Title");
			movie.setDirector("Mock Director");
			movie.setGenre(Genre.ACTION);
			movie.setCountry("South Korea");
			movie.setLanguage("English");
			movie.setRuntime(60);
			movie.setRelease(releaseDate);
			
			// Add movie
			WebTarget target = client.target(IMJApplication.BASEURI + "/movie");
			Response response = target.request().post(Entity.xml(movie));
			
			String location = response.getLocation().toString();
			
			response.close();
			
			logger.info("URI for new movie is: " + location);
			
			String[] split = location.split("/");
			
			createdMovieId = Long.parseLong(split[split.length-1]);
			createdMovie = true;
			
			Map<String, Object> resolveTemplateMap = new HashMap<String, Object>();
			resolveTemplateMap.put("viewerId", viewer.getId());
			resolveTemplateMap.put("movieId", createdMovieId);

			// Subscribing to movie. Get notification
			final WebTarget targetNotification = client.target(IMJApplication.BASEURI + "/viewer/{viewerId:\\d+}/recommended/{movieId:\\d+}").resolveTemplates(resolveTemplateMap);
			
//			targetNotification.request().async().get(new InvocationCallback<String>() {
//
//				public void completed(String message) {
//					logger.info("Received callback message for movie subscription");
//					logger.info(message);
//				}
//
//				public void failed(Throwable t) {
//					t.printStackTrace();
//					System.out.println("error");
//				}
//
//			});

			final Future<String> future1 = targetNotification.request().async().get(new InvocationCallback<String>() {
				
				public void completed(String message) {
					logger.info("Received callback message for movie subscription");
					logger.info(message);
				}
				
				public void failed(Throwable t) {}
				
			});
			
			logger.info("Waiting for movie subscription callback. Must wait " + timeDifference + "s");
			
			future1.get();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		
	}
}
