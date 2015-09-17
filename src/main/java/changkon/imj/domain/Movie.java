package changkon.imj.domain;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * Movie class representing a movie, containing relevant information about the film
 * @author Chang Kon Han
 */

//@Entity
//@Table(name="MOVIE")
public class Movie {
	private long id;
	private String title;
	private String director;
	private List<String> cast;
	private String description;
	private Genre genre;
	private Map<String, DateTime> release;
	private String country;
	private String language;
	private int runtime;
	private URL poster;
	
	/**
	 * Default constructor for movie. JavaBean convention
	 */
	public Movie() {}
	
	/**
	 * Movie constructor with initialization values for variables
	 * @param id
	 * @param title
	 * @param director
	 * @param cast
	 * @param description
	 * @param release
	 * @param country
	 * @param language
	 * @param runtime
	 * @param poster
	 */
	public Movie(
			long id,
			String title,
			String director,
			List<String> cast,
			String description,
			Genre genre,
			Map<String, DateTime> release,
			String country,
			String language,
			int runtime,
			URL poster
			) {
		
		this.id = id;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.description = description;
		this.genre = genre;
		this.release = release;
		this.country = country;
		this.language = language;
		this.runtime = runtime;
		this.poster = poster;
	}
	
	/**
	 * @return Id of movie
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets id of movie
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Title of Movie
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of movie
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Director of movie
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets director of movie
	 * @param director
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return Cast for movie
	 */
	public List<String> getCast() {
		return cast;
	}

	/**
	 * Sets cast of movie
	 * @param cast
	 */
	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	/**
	 * Adds cast member to movie cast
	 * @param member
	 */
	public void addCastMember(String member) {
		cast.add(member);
	}
	
	/**
	 * Removes member from cast
	 * @param member
	 */
	public void removeCastMember(String member) {
		cast.remove(member);
	}
	
	/**
	 * @return Description of movie
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description of movie
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Genre of film
	 */
	public Genre getGenre() {
		return genre;
	}
	
	/**
	 * Sets genre of film
	 * @param genre
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	/**
	 * Get release dates for movie
	 * @return
	 */
	public Map<String, DateTime> getRelease() {
		return release;
	}

	/**
	 * Set release date for movie
	 * @param release
	 */
	public void setRelease(Map<String, DateTime> release) {
		this.release = release;
	}

	/**
	 * Add release date to movie
	 * @param country
	 * @param date
	 */
	public void addReleaseDate(String country, DateTime date) {
		release.put(country, date);
	}
	
	/**
	 * Removes release date from movie
	 * @param country
	 */
	public void removeReleaseDate(String country) {
		release.remove(country);
	}
	
	/**
	 * @return Country of origin of movie
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set country of origin of movie
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Language of movie
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Get language for movie
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return Runtime of movie
	 */
	public int getRuntime() {
		return runtime;
	}
	
	/**
	 * Sets runtime of movie
	 * @param runtime
	 */
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return Url of poster
	 */
	public URL getPoster() {
		return poster;
	}

	/**
	 * Sets url of poster
	 * @param poster
	 */
	public void setPoster(URL poster) {
		this.poster = poster;
	}
}
