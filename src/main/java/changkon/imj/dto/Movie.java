package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import changkon.imj.domain.Genre;

/**
 * Movie class representing a movie.
 * 
 * DTO representation of movie.
 * @author Chang Kon Han
 */

@XmlRootElement(name="movie")
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {
	
	@XmlAttribute(name="id")
	private Long id;
	
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
			Long id,
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
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets id of movie
	 * @param id
	 */
	public void setId(Long id) {
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

	/**
	 * Returns if movie is equal to another movie
	 * @param other
	 * @return
	 */
	public boolean equals(Movie other) {
		
		if (!(title.equals(other.getTitle()))) {
			return false;
		}
		
		if (!(director.equals(other.getDirector()))) {
			return false;
		}
		
		if (!(genre == other.getGenre())) {
			return false;
		}
		
		if (!(language.equals(other.getLanguage()))) {
			return false;
		}
		
		if (!(country.equals(other.getCountry()))) {
			return false;
		}
		
		if (!(runtime == other.getRuntime())) {
			return false;
		}
		
		if (!(release.toLocalDate().equals(other.getRelease().toLocalDate()))) {
			return false;
		}
		
		return true;
	}
	
}