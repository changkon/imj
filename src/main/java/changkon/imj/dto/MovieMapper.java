package changkon.imj.dto;
import changkon.imj.domain.Movie;

public class MovieMapper {
	public static Movie toDomainModel(changkon.imj.dto.Movie dtoMovie) {
		Movie movie = new Movie();
		
		movie.setId(dtoMovie.getId());
		movie.setTitle(dtoMovie.getTitle());
		movie.setDirector(dtoMovie.getDirector());
		movie.addReleaseDate(dtoMovie.getCountry(), dtoMovie.getRelease());
		movie.setCountry(dtoMovie.getCountry());
		movie.setGenre(dtoMovie.getGenre());
		movie.setLanguage(dtoMovie.getLanguage());
		movie.setRuntime(dtoMovie.getRuntime());
		
		return movie;
	}
}
