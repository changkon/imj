package changkon.imj.dto;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	
	public static Map<String, Date> toDateUtil(Map<String, DateTime> dtoRelease) {
		Map<String, Date> releaseDates = new HashMap<String, Date>();
		
		for (Map.Entry<String, DateTime> entry : dtoRelease.entrySet()) {
			releaseDates.put(entry.getKey(), entry.getValue().toDate());
		}
		
		return releaseDates;
	}
	
	public static Map<String, DateTime> toDateJoda(Map<String, Date> release) {
		Map<String, DateTime> releaseDates = new HashMap<String, DateTime>();
		
		for (Map.Entry<String, Date> entry : release.entrySet()) {
			releaseDates.put(entry.getKey(), new DateTime(entry.getValue()));
		}
		
		return releaseDates;
	}
}
