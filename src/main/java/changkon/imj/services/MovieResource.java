package changkon.imj.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieMapper;
import changkon.imj.dto.Movies;

public class MovieResource implements IMovieResource {

	private static Logger logger = LoggerFactory.getLogger(MovieResource.class);
	
	public Response createMovie(Movie dtoMovie) {
		// Change dto movie instance into domain model which will be persisted into database
		changkon.imj.domain.Movie movie = MovieMapper.toDomainModel(dtoMovie);
		
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
		} finally {
			if (em != null || em.isOpen()) {
				em.close();
			}
		}
		
		return Response.created(URI.create("/movie/" + movie.getId())).build();
	}

	public Movies queryMovieList() {
		Movies movies = new Movies();
		
		List<Movie> movieList = new ArrayList<Movie>();
		EntityManager em = Persistence.createEntityManagerFactory(IMJApplication.PERSISTENCEUNIT).createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			
			// If I put query with lock type, it throws error
			List<changkon.imj.domain.Movie> domainMovieList = em.createQuery("SELECT m FROM Movie m", changkon.imj.domain.Movie.class)
					.getResultList();
			
			for (changkon.imj.domain.Movie movie : domainMovieList) {
				movieList.add(MovieMapper.toDTOModel(movie));
			}
			
			tx.commit();
		} finally {
			if (em != null || em.isOpen()) {
				em.close();
			}
			movies.setMovies(movieList);
		}
		
		return movies;
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
			
		} finally {
			if (em != null || em.isOpen()) {
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
			
		} finally {
			if (em != null || em.isOpen()) {
				em.close();
			}
		}
		
		// TODO Auto-generated method stub
	}

}
