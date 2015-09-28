package changkon.imj.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import changkon.imj.domain.Movie;

public class OMDBResource implements IOMDBResource {
	
	/**
	 * Returns the URL for the Client to access
	 * @param title
	 * @return OMDB api call
	 */
	public String getURL(String title) {
		return "http://www.omdbapi.com/?t=" + title + "&plot=full";
	}
	
	@Override
	public void queryMovie(final long id, final @Suspended AsyncResponse response) {
		new Thread(
			new Runnable() {

				@Override
				public void run() {
					EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
					
					final Client client = ClientBuilder.newClient();
					final String movieTitle;
					
					try {
						EntityTransaction tx = em.getTransaction();

						tx.begin();
						
						Movie domainMovie = em.find(Movie.class, id);
						movieTitle = domainMovie.getTitle();
						
						tx.commit();
					} finally {
						if (em.isOpen() || em != null) {
							em.close();
						}
					}
					
					final String json;
					System.out.println("called");
					// Set up connection to OMDB api
					String url = getURL(movieTitle);
					System.out.println(url);
					WebTarget target = client.target(url);
					Future<Response> future = target.request().accept(MediaType.APPLICATION_JSON).async().get();

					try {
						
						json = future.get().readEntity(String.class);
						System.out.println("Json: ");
						System.out.println(json);
						client.close();
						response.resume(json);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		).start();
	}
}
