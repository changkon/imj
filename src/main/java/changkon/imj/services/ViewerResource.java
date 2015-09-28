package changkon.imj.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.domain.Genre;
import changkon.imj.dto.Log;
import changkon.imj.dto.Movie;
import changkon.imj.dto.Movies;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.ViewerLogs;
import changkon.imj.dto.ViewerRecommendedMovies;
import changkon.imj.dto.Viewers;
import changkon.imj.mapper.LogMapper;
import changkon.imj.mapper.MovieMapper;
import changkon.imj.mapper.ViewerMapper;

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
		boolean errorThrown = false;
		
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();

		try {

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			changkon.imj.domain.Viewer viewer = em.find(changkon.imj.domain.Viewer.class, id);
			dtoViewer = ViewerMapper.toDTOModel(viewer);

			tx.commit();

		} catch (Exception e) {
			logger.error("Failed to query viewer information");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}

		return (errorThrown == true) ? null : dtoViewer;
	}

	public Response createLog(long viewerId, Log log) {
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

		return (errorThrown == true) ? null : Response.created(URI.create("/viewer/" + log.getViewer().getId() + "/log")).build();
	}

	@Override
	public ViewerLogs queryLogs(long viewerId) {
		ViewerLogs viewerLogs = new ViewerLogs();
		boolean errorThrown = false;
		
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
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}

		return (errorThrown == true) ? null : viewerLogs;
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
		ViewerRecommendedMovies recommendedMovies = new ViewerRecommendedMovies();
		boolean errorThrown = false;
		
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();

		try {

			EntityTransaction tx = em.getTransaction();
			tx.begin();

			changkon.imj.domain.Viewer domainViewer = em.find(changkon.imj.domain.Viewer.class, viewerId);

			Set<changkon.imj.domain.Movie> domainMovieSet = domainViewer.getRecommendedMovies();

			Set<Movie> movieSet = new HashSet<Movie>();
			for (changkon.imj.domain.Movie movie : domainMovieSet) {
				movieSet.add(MovieMapper.toDTOModel(movie));
			}

			recommendedMovies.setRecommendedMovies(movieSet);

		} catch (Exception e) {
			logger.error("Failed to query viewers recommended movies");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}

		return (errorThrown == true) ? null : recommendedMovies;
	}

	public void movieNotification(final long viewerId, final long movieId, @Suspended final AsyncResponse response) {

		new Thread(
			new Runnable() {

				public void run() {

					EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();

					Map<String, Date> releaseDates = null;
					Date movieTime = null;
					DateTime movieTimeJoda;
					String viewerCountry = "";
					String movieTitle = "";

					try {
						EntityTransaction tx = em.getTransaction();

						tx.begin();

						changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, movieId);
						changkon.imj.domain.Viewer domainViewer = em.find(changkon.imj.domain.Viewer.class, viewerId);

						// Get viewer country
						viewerCountry = domainViewer.getCountry();

						// Get domain movie release dates
						releaseDates = domainMovie.getRelease();

						movieTitle = domainMovie.getTitle();

						tx.commit();

					} catch (Exception e) {

					} finally {
						if (em.isOpen() || em != null) {
							em.close();
						}
					}

					// Gets the current time in joda time
					DateTime now = DateTime.now();

					movieTime = releaseDates.get(viewerCountry);

					// There is key which matches viewers country
					if (movieTime == null) {
						Map.Entry<String, Date> entry = releaseDates.entrySet().iterator().next();

						// Get first movie time
						movieTimeJoda = new DateTime(entry.getValue());

					} else {
						movieTimeJoda = new DateTime(movieTime);
					}

					final String movieTitleFinal = movieTitle;

					// Runnable which resumes async response
					Runnable task = new Runnable() {

						@Override
						public void run() {
							String message = movieTitleFinal + " is now ready to watch";
							response.resume(message);
						}

					};

					// Check if movie time is after current time. Only subscribe if movie hasn't been released yet
					if (movieTimeJoda.isAfter(now)) {
						// Find difference in seconds
						Seconds difference = Seconds.secondsBetween(now, movieTimeJoda);
						ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
						executor.schedule(task, difference.getSeconds(), TimeUnit.SECONDS);
					} else {
						response.resume("Movie is already out");
					}
				}
			}
		).start();
	}

	@Override
	public Viewers queryViewerList() {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		Viewers viewers = new Viewers();
		boolean errorThrown = false;
		try {
			
			List<Viewer> viewerList = new ArrayList<Viewer>();
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// If I put query with lock type, it throws error
			List<changkon.imj.domain.Viewer> domainViewerList = em.createQuery("SELECT v FROM Viewer v", changkon.imj.domain.Viewer.class)
					.getResultList();
			
			for (changkon.imj.domain.Viewer viewer : domainViewerList) {
				viewerList.add(ViewerMapper.toDTOModel(viewer));
			}
			
			tx.commit();
			
			viewers.setViewers(viewerList);
			
		} catch (Exception e) {
			logger.error("Querying viewer list resulted in error");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true) ? null : viewers;
	}

	@Override
	public Viewers queryViewerList(int start, int size) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		Viewers viewers = new Viewers();
		boolean errorThrown = false;
		try {
			
			List<Viewer> viewerList = new ArrayList<Viewer>();
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// String query
			String sql = "SELECT v "
					+ "FROM Viewer v "
					+ "WHERE v.id >= " + start + " "
					+ "ORDER BY v.id";
			
			Query query = em.createQuery(sql, changkon.imj.domain.Viewer.class);
			query.setMaxResults(size);
			List<changkon.imj.domain.Viewer> domainViewerList = query.getResultList();
			
			for (changkon.imj.domain.Viewer viewer : domainViewerList) {
				viewerList.add(ViewerMapper.toDTOModel(viewer));
			}
			
			tx.commit();
			
			viewers.setViewers(viewerList);
			
			return viewers;
		} catch (Exception e) {
			logger.error("Querying viewer list resulted in error");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true) ? null : viewers;
	}
}
