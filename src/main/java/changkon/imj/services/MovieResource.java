package changkon.imj.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import changkon.imj.dto.Movie;
import changkon.imj.dto.MovieMapper;

public class MovieResource implements IMovieResource {

	private static Logger logger = LoggerFactory.getLogger(MovieResource.class);
	
	public Movie createMovie(Movie dtoMovie) {
		changkon.imj.domain.Movie movie = MovieMapper.toDomainModel(dtoMovie);
		
		// put movie instance into database
		EntityManager em = Persistence.createEntityManagerFactory("imj").createEntityManager();
		
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
		
		return dtoMovie;
	}

	public Movie queryMovieList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Movie queryMovie(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Movie updateMovie(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
