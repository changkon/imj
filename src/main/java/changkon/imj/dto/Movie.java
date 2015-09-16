package changkon.imj.dto;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

import changkon.imj.domain.Genre;

/**
 * Movie class representing a movie.
 * 
 * DTO representation of movie.
 * @author Chang Kon Han
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {
	
	@XmlAttribute
	private long id;
	
	@XmlElement
	private String title;
	
	@XmlElement
	private String director;
	
	@XmlElement
	private Genre genre;
	
	@XmlElement
	private DateTime release;
	
	@XmlElement
	private String country;
	
	@XmlElement
	private String language;
	
	@XmlElement
	private int runtime;
	
	/**
	 * Default constructor. JavaBean convention
	 */
	public Movie() {}
	
	/**
	 * Movie constructor with initialization values for variables
	 * @param id
	 * @param title
	 * @param director
	 * @param genre
	 * @param release
	 * @param country
	 * @param language
	 * @param runtime
	 */
	public Movie(
			long id,
			String title,
			String director,
			Genre genre,
			DateTime release,
			String country,
			String language,
			int runtime) {
		
		this.id = id;
		this.title = title;
		this.director = director;
		this.release = release;
		this.country = country;
		this.language = language;
		this.runtime = runtime;
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
	public DateTime getRelease() {
		return release;
	}

	/**
	 * Set release date for movie
	 * @param release
	 */
	public void setRelease(DateTime release) {
		this.release = release;
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

}