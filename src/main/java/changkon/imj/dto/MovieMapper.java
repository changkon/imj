package changkon.imj.dto;
import java.util.Date;

import org.joda.time.DateTime;

import changkon.imj.domain.Movie;

public class MovieMapper {
	public static Movie toDomainModel(changkon.imj.dto.Movie dtoMovie) {
		Movie movie = new Movie();
		
		movie.setId(dtoMovie.getId());
		movie.setTitle(dtoMovie.getTitle());
		movie.setDirector(dtoMovie.getDirector());
		movie.addReleaseDate(dtoMovie.getCountry(), dtoMovie.getRelease().toDate());
		movie.setCountry(dtoMovie.getCountry());
		movie.setGenre(dtoMovie.getGenre());
		movie.setLanguage(dtoMovie.getLanguage());
		movie.setRuntime(dtoMovie.getRuntime());
		
		return movie;
	}
	
	public static changkon.imj.dto.Movie toDTOModel(Movie movie) {
		changkon.imj.dto.Movie dtoMovie = new changkon.imj.dto.Movie();
		
		dtoMovie.setId(movie.getId());
		dtoMovie.setTitle(movie.getTitle());
		dtoMovie.setDirector(movie.getDirector());
		
		Date date = movie.getRelease().get(movie.getCountry());
		
		dtoMovie.setRelease(new DateTime(date));
		dtoMovie.setCountry(movie.getCountry());
		dtoMovie.setGenre(movie.getGenre());
		dtoMovie.setLanguage(movie.getLanguage());
		dtoMovie.setRuntime(movie.getRuntime());
		
		return dtoMovie;
	}
}
