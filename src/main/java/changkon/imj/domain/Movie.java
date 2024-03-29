package changkon.imj.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Movie class representing a movie, containing relevant information about the film
 * @author Chang Kon Han
 */

@Entity
@Table(name="MOVIE")
public class Movie {
	
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@Column(name="ID")
	private Long id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DIRECTOR")
	private String director;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="CAST")
	@Column(name="NAME")
	@org.hibernate.annotations.CollectionId(
			columns = @Column(name="CAST_ID"),
			type = @org.hibernate.annotations.Type(type="long"),
			generator = "ID_GENERATOR")
	private Collection<String> cast = new ArrayList<String>();
	
	@Column(name="DESCRIPTION", length=1000)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name="GENRE")
	private Genre genre;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="RELEASES")
	@MapKeyColumn(name="COUNTRY")
	@Column(name="DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Map<String, Date> releases = new HashMap<String, Date>();
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="LANGUAGE")
	private String language;
	
	@Column(name="RUNTIME")
	private int runtime;
	
	@Column(name="URL", nullable=true)
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
			Long id,
			String title,
			String director,
			Collection<String> cast,
			String description,
			Genre genre,
			Map<String, Date> releases,
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
		this.releases = releases;
		this.country = country;
		this.language = language;
		this.runtime = runtime;
		this.poster = poster;
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
	 * @return Cast for movie
	 */
	public Collection<String> getCast() {
		return cast;
	}

	/**
	 * Sets cast of movie
	 * @param cast
	 */
	public void setCast(Collection<String> cast) {
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
	public Map<String, Date> getRelease() {
		return releases;
	}

	/**
	 * Set release date for movie
	 * @param release
	 */
	public void setRelease(Map<String, Date> release) {
		this.releases = release;
	}

	/**
	 * Add release date to movie
	 * @param country
	 * @param date
	 */
	public void addReleaseDate(String country, Date date) {
		releases.put(country, date);
	}
	
	/**
	 * Removes release date from movie
	 * @param country
	 */
	public void removeReleaseDate(String country) {
		releases.remove(country);
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
