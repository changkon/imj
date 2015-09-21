package changkon.imj.services;

import changkon.imj.domain.Movie;
import changkon.imj.dto.MovieMapper;

public class MovieResource implements IMovieResource {

	public Movie createMovie(changkon.imj.dto.Movie dtoMovie) {
		Movie movie = MovieMapper.toDomainModel(dtoMovie);
		return movie;
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
