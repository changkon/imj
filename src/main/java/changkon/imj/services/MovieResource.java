package changkon.imj.services;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieCast;
import changkon.imj.dto.MovieDescription;
import changkon.imj.dto.MoviePoster;
import changkon.imj.dto.MovieReleaseDates;
import changkon.imj.dto.Movies;
import changkon.imj.dto.Viewer;
import changkon.imj.dto.Viewers;
import changkon.imj.mapper.MovieMapper;
import changkon.imj.mapper.ViewerMapper;

public class MovieResource implements IMovieResource {

	private static Logger logger = LoggerFactory.getLogger(MovieResource.class);
	
	public Response createMovie(Movie dtoMovie) {
		// Change dto movie instance into domain model which will be persisted into database
		changkon.imj.domain.Movie movie = MovieMapper.toDomainModel(dtoMovie);
		
		boolean errorThrown = false;
		
		// put movie instance into database
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// Persist the Movie entry into database
			em.persist(movie);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error occurred in creating Movie");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true) ? Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() : Response.created(URI.create("/movie/" + movie.getId())).build();
	}

	public void deleteMovie(long id) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();

		try {

			EntityTransaction tx = em.getTransaction();

			tx.begin();

			// First find movie resource to delete
			changkon.imj.domain.Movie movie = em.find(changkon.imj.domain.Movie.class, id);

			// Delete movie from database
			em.remove(movie);

			// Commit transaction
			tx.commit();

		} catch (Exception e) {
			logger.error("Failed to delete movie");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}
	
	public Movies queryMovieList() {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		Movies movies = new Movies();
		boolean errorThrown = false;
		try {
			
			
			List<Movie> movieList = new ArrayList<Movie>();
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// If I put query with lock type, it throws error
			List<changkon.imj.domain.Movie> domainMovieList = em.createQuery("SELECT m FROM Movie m", changkon.imj.domain.Movie.class)
					.getResultList();
			
			for (changkon.imj.domain.Movie movie : domainMovieList) {
				movieList.add(MovieMapper.toDTOModel(movie));
			}
			
			tx.commit();
			
			movies.setMovies(movieList);
			
		} catch (Exception e) {
			logger.error("Querying movie list resulted in error");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || movies.getMovies().isEmpty()) ? null : movies;
	}

	public Movies queryMovieList(int start, int size) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		Movies movies = new Movies();
		boolean errorThrown = false;
		try {
			
			List<Movie> movieList = new ArrayList<Movie>();
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// String query
			String sql = "SELECT m "
					+ "FROM Movie m "
					+ "WHERE m.id >= " + start + " "
					+ "ORDER BY m.id";
			
			Query query = em.createQuery(sql, changkon.imj.domain.Movie.class);
			query.setMaxResults(size);
			List<changkon.imj.domain.Movie> domainMovieList = query.getResultList();
			
			for (changkon.imj.domain.Movie movie : domainMovieList) {
				movieList.add(MovieMapper.toDTOModel(movie));
			}
			
			tx.commit();
			
			movies.setMovies(movieList);
			
		} catch (Exception e) {
			logger.error("Querying movie list resulted in error");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || movies.getMovies().isEmpty()) ? null : movies;
	}
	
	public Movie queryMovie(long id) {
		Movie dtoMovie = null;
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Movie movie = em.find(changkon.imj.domain.Movie.class, id);
			dtoMovie = MovieMapper.toDTOModel(movie);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error querying movie");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return dtoMovie;
	}

	public void updateMovie(long id, Movie movie) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// Get movie indicated by id
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			
			// Update fields in domainMovie representation
			domainMovie.setTitle(movie.getTitle());
			domainMovie.setDirector(movie.getDirector());
			domainMovie.setCountry(movie.getCountry());
			domainMovie.setGenre(movie.getGenre());
			domainMovie.setLanguage(movie.getLanguage());
			domainMovie.setRuntime(movie.getRuntime());
			
			Map<String, Date> release = new HashMap<String, Date>();
			release.put(movie.getCountry(), movie.getRelease().toDate());
			
			domainMovie.setRelease(release);
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating movie");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	public MovieCast queryCast(long id) {
		MovieCast movieCast = new MovieCast();
		boolean errorThrown = false;
		
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			
			movieCast.setCast(domainMovie.getCast());
			
			tx.commit();
		} catch (Exception e) {
			logger.error("Error retrieving movie cast");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || movieCast.getCast().isEmpty()) ? null : movieCast;
	}
	
	public void updateCast(long id, MovieCast cast) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			// Get movie indicated by id
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			
			domainMovie.setCast(cast.getCast());
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating cast for movie");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	public MovieDescription queryDescription(long id) {
		MovieDescription description = new MovieDescription();
		boolean errorThrown = false;
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			
			description.setDescription(domainMovie.getDescription());
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error querying movie description");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || description.getDescription().equals("") || description.getDescription() == null) ? null : description;
	}
	
	public void updateDescription(long id, MovieDescription description) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			// Get movie indicated by id
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			domainMovie.setDescription(description.getDescription());
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating description");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	public MovieReleaseDates queryReleaseDates(long id) {
		MovieReleaseDates releaseDates = new MovieReleaseDates();
		boolean errorThrown = false;
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			changkon.imj.domain.Movie movie = em.find(changkon.imj.domain.Movie.class, id);
			Map<String, DateTime> releaseDatesMap = MovieMapper.toDateJoda(movie.getRelease());
			releaseDates.setReleases(releaseDatesMap);
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error querying release dates");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || releaseDates.getReleases().isEmpty()) ? null : releaseDates;
	}

	public void updateReleaseDates(long id, MovieReleaseDates movieReleaseDates) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			changkon.imj.domain.Movie movie = em.find(changkon.imj.domain.Movie.class, id);
			Map<String, Date> releaseDatesMap = MovieMapper.toDateUtil(movieReleaseDates.getReleases());
			movie.setRelease(releaseDatesMap);
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating release dates");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

	public MoviePoster queryPoster(long id) {
		MoviePoster poster = new MoviePoster();
		boolean errorThrown = false;
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			changkon.imj.domain.Movie domainMovie = em.find(changkon.imj.domain.Movie.class, id);
			
			poster.setUrl(domainMovie.getPoster().toString());;
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error querying movie poster");
			errorThrown = true;
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
		
		return (errorThrown == true || poster.getUrl().equals("") || poster.getUrl() == null) ? null : poster;
	}

	public void updatePoster(long id, MoviePoster poster) {
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			// Start a transaction to persist Movie object
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			changkon.imj.domain.Movie movie = em.find(changkon.imj.domain.Movie.class, id);
			
			movie.setPoster(new URL(poster.getUrl()));
			
			tx.commit();
			
		} catch (Exception e) {
			logger.error("Error updating movie poster");
		} finally {
			if (em.isOpen() || em != null) {
				em.close();
			}
		}
	}

}
