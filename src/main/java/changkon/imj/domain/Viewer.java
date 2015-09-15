package changkon.imj.domain;

import java.util.List;

import org.apache.commons.logging.Log;

/**
 * Movie viewer. Viewer is entity which watches movie
 * @author Chang Kon Han
 */
public class Viewer extends Person implements Subscriber {
	private long id;
	private List<Log> movieLog;
	private List<Movie> recommendedMovies;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public Viewer() {}
	
	/**
	 * @return Id of Viewer
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Set Id of Viewer
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return Movie Log for viewer
	 */
	public List<Log> getMovieLog() {
		return movieLog;
	}
	
	/**
	 * Sets movie log for viewer
	 * @param movieLog
	 */
	public void setMovieLog(List<Log> movieLog) {
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
	public List<Movie> getRecommendedMovies() {
		return recommendedMovies;
	}
	
	/**
	 * Sets recommended movies for viewer
	 * @param recommendedMovies
	 */
	public void setRecommendedMovies(List<Movie> recommendedMovies) {
		this.recommendedMovies = recommendedMovies;
	}
	
	public void notification() {
		// TODO Auto-generated method stub
		
	}
	
}
