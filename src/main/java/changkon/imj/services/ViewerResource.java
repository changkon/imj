package changkon.imj.services;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.dto.Log;
import changkon.imj.dto.LogMapper;
import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieMapper;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.ViewerLogs;
import changkon.imj.dto.ViewerMapper;
import changkon.imj.dto.ViewerRecommendedMovies;
import changkon.imj.jaxb.JAXBMarshalPrint;

public class ViewerResource implements IViewerResource {

	private static Logger logger = LoggerFactory.getLogger(ViewerResource.class);
	
	@Override
	public Response createViewer(Viewer dtoViewer) {
		// Change dto viewer instance into domain model which will be persisted into database
		changkon.imj.domain.Viewer viewer = ViewerMapper.toDomainModel(dtoViewer);
		
		// put movie instance into database
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// Persist the Movie entry into database
			em.persist(viewer);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error occurred in creating viewer");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return Response.created(URI.create("/viewer/" + viewer.getId())).build();
	}

	@Override
	public void deleteViewer(long id) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// First find viewer resource to delete
			changkon.imj.domain.Viewer viewer = em.find(changkon.imj.domain.Viewer.class, id);
			
			// Delete viewer from database
			em.remove(viewer);
			
			// Commit transaction
			tx.commit();
		
		} catch (Exception e) {
			logger.error("Failed to delete viewer");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
	}

	@Override
	public void updateViewer(long id, Viewer viewer) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// Get movie indicated by id
			changkon.imj.domain.Viewer domainViewer = em.find(changkon.imj.domain.Viewer.class, id);
			
			// Update fields in domainMovie representation
			domainViewer.setAge(viewer.getAge());
			domainViewer.setCountry(viewer.getCountry());
			domainViewer.setFirstName(viewer.getFirstName());
			domainViewer.setLastName(viewer.getLastName());
			domainViewer.setGender(viewer.getGender());
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating movie");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	@Override
	public Viewer queryViewer(long id) {
		Viewer dtoViewer = new Viewer();
		
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Viewer viewer = em.find(changkon.imj.domain.Viewer.class, id);
			dtoViewer = ViewerMapper.toDTOModel(viewer);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Failed to query viewer information");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return dtoViewer;
	}

	@Override
	public Log createLog(long viewerId, Log log) {
		boolean errorThrown = false;
		
		Log copy = new Log();
		copy.setDate(log.getDate());
		copy.setGeoLocation(log.getGeolocation());
		copy.setMovie(log.getMovie());

		// put log instance into database
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			changkon.imj.domain.Viewer viewer = em.find(changkon.imj.domain.Viewer.class, viewerId);
			
			// Add dto
			copy.setViewer(ViewerMapper.toDTOModel(viewer));

			changkon.imj.domain.Log domainLog = LogMapper.toDomainModel(copy);

			// id values don't match
			if (viewerId != viewer.getId()) {
				return null;
			}

			viewer.addMovieLog(domainLog);

			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error occurred in creating movie log for viewer");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true) ? null : copy;
	}

	@Override
	public ViewerLogs queryLogs(long viewerId) {
		ViewerLogs viewerLogs = new ViewerLogs();
		
		// put movie instance into database
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// Get viewer from database
			changkon.imj.domain.Viewer viewer = em.find(changkon.imj.domain.Viewer.class, viewerId);
			
			Set<Log> dtoLogSet = new HashSet<Log>();
			
			for (changkon.imj.domain.Log log : viewer.getMovieLog()) {
				dtoLogSet.add(LogMapper.toDTOModel(log));
			}
			
			viewerLogs.setMovieLog(dtoLogSet);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error querying user movie logs");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return viewerLogs;
	}

	@Override
	public void updateRecommended(long viewerId, ViewerRecommendedMovies recommendedMovies) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Viewer domainViewer = em.find(changkon.imj.domain.Viewer.class, viewerId);
			
			Set<changkon.imj.domain.Movie> domainRecommendedMovieSet = new HashSet<changkon.imj.domain.Movie>();
			
			Set<Movie> recommendedMovieSet = recommendedMovies.getRecommendedMovies();
			
			for (Movie movie : recommendedMovieSet) {
				domainRecommendedMovieSet.add(MovieMapper.toDomainModel(movie));
			}
			
			domainViewer.setRecommendedMovies(domainRecommendedMovieSet);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating viewers recommended movies");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	@Override
	public ViewerRecommendedMovies queryRecommended(long viewerId) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Viewer domainViewer = em.find(changkon.imj.domain.Viewer.class, viewerId);
			
			ViewerRecommendedMovies recommendedMovies = new ViewerRecommendedMovies();
			
			Set<changkon.imj.domain.Movie> domainMovieSet = domainViewer.getRecommendedMovies();
			
			Set<Movie> movieSet = new HashSet<Movie>();
			for (changkon.imj.domain.Movie movie : domainMovieSet) {
				movieSet.add(MovieMapper.toDTOModel(movie));
			}
			
			recommendedMovies.setRecommendedMovies(movieSet);
			
			return recommendedMovies;
			
		} catch (Exception e) {
			logger.error("Failed to query viewers recommended movies");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return null;
	}

}
