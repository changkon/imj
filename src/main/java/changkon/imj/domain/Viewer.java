package changkon.imj.domain;

import java.util.Set;

import org.apache.commons.logging.Log;

/**
 * Movie viewer. Viewer is entity which watches movie
 * @author Chang Kon Han
 */
public class Viewer extends Person implements Subscriber {
	private Set<Log> movieLog;
	private Set<Movie> recommendedMovies;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public Viewer() {}
	
	/**
	 * @return Movie Log for viewer
	 */
	public Set<Log> getMovieLog() {
		return movieLog;
	}
	
	/**
	 * Sets movie log for viewer
	 * @param movieLog
	 */
	public void setMovieLog(Set<Log> movieLog) {
		this.movieLog = movieLog;
	}
	
	/**
	 * Adds movie log entry to viewers movie log
	 * @param log
	 */
	public void addMovieLog(Log log) {
		movieLog.add(log);
	}
	
	/**
	 * Removes movie log entry from viewers movie log
	 * @param log
	 */
	public void removeMovieLog(Log log) {
		movieLog.remove(log);
	}
	
	/**
	 * @return Recommended movies for viewer
	 */
	public Set<Movie> getRecommendedMovies() {
		return recommendedMovies;
	}
	
	/**
	 * Sets recommended movies for viewer
	 * @param recommendedMovies
	 */
	public void setRecommendedMovies(Set<Movie> recommendedMovies) {
		this.recommendedMovies = recommendedMovies;
	}
	
	public void notification() {
		// TODO Auto-generated method stub
		
	}
	
}
