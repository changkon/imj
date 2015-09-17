package changkon.imj.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

/**
 * Log entry of movie viewing
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="LOG")
public class Log {
	
	@XmlElement
	private DateTime date;
	
	@XmlElement(name="geo-location")
	@Embedded
	private GeoLocation geoLocation;
	
	@XmlElement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="VIEWER_ID", nullable=false)
	private Viewer viewer;
	
	@XmlElement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MOVIE_ID", nullable=false)
	private Movie movie;
	
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
	public Log(DateTime date, GeoLocation geoLocation, Viewer viewer, Movie movie) {
		this.date = date;
		this.geoLocation = geoLocation;
		this.viewer = viewer;
		this.movie = movie;
	}

	/**
	 * @return Date of Log entry
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * Sets date of Log entry
	 * @param date
	 */
	public void setDate(DateTime date) {
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