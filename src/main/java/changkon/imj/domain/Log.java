package changkon.imj.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Log entry of movie viewing
 * @author Chang Kon Han
 */
@Entity
@Access(AccessType.FIELD)
public class Log {
	
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="VIEWER_ID", nullable=false)
	private Viewer viewer;
	
	@OneToOne(
			optional=false,
			cascade=CascadeType.PERSIST)
	@JoinColumn(name="MOVIE_ID", nullable=false)
	private Movie movie;
	
	@Column(nullable=false, name="DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Embedded
	private GeoLocation geoLocation;
	
	/**
	 * Default constructor. JavaBean convention
	 */
	public Log() {}
	
	/**
	 * Log constructor with initialization values for variables
	 * @param date
	 * @param geoLocation
	 * @param viewer
	 * @param movie
	 */
	public Log(Date date, GeoLocation geoLocation, Viewer viewer, Movie movie) {
		this.date = date;
		this.geoLocation = geoLocation;
		this.viewer = viewer;
		this.movie = movie;
	}

	/**
	 * @return Date of Log entry
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets date of Log entry
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Geographic location of Log entry
	 */
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	/**
	 * Sets geographic location of Log entry
	 * @param geoLocation
	 */
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * @return Viewer entity of Log
	 */
	public Viewer getViewer() {
		return viewer;
	}

	/**
	 * Sets viewer for Log
	 * @param viewer
	 */
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * @return Movie of Log
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Sets movie for Log
	 * @param movie
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}
