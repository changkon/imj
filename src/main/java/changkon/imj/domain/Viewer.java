package changkon.imj.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.slf4j.LoggerFactory;

/**
 * Movie viewer. Viewer is entity which watches movie
 * @author Chang Kon Han
 */
@Entity
@Table(name="VIEWER")
public class Viewer extends Person implements Subscriber {
	
	@OneToMany(mappedBy="viewer", fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Log> movieLog = new HashSet<Log>();
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinTable(
			name="RECOMMENDEDMOVIES",
			joinColumns=@JoinColumn(name="VIEWER_ID"),
			inverseJoinColumns=@JoinColumn(name="MOVIE_ID"))
	private Set<Movie> recommendedMovies = new HashSet<Movie>();
	
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
		log.setViewer(this);
	}
	
	/**
	 * Removes movie log entry from viewers movie log
	 * @param log
	 */
	public void removeMovieLog(Log log) {
		movieLog.remove(log);
		log.setViewer(null);
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
		LoggerFactory.getLogger(Viewer.class).info("Notification received");
	}

	/**
	 * Subscribes to movie by adding movie to recommended list
	 */
	public void subscribe(Movie movie) {
		recommendedMovies.add(movie);
	}

	/**
	 * Unsubscribes to movie by removing movie from recommended list
	 */
	public void unsubscribe(Movie movie) {
		recommendedMovies.remove(movie);
	}
	
}
