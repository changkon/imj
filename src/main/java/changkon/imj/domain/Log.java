package changkon.imj.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

/**
 * Log entry of movie viewing
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Log {
	
	@XmlTransient
	@Column(nullable=false, name="VIEWER_ID")
	private Viewer viewer;
	
	@XmlElement
	@Column(nullable=false, name="MOVIE_ID")
	private Movie movie;
	
	@XmlElement
	@Column(nullable=false, name="DATE")
	private DateTime date;
	
	@XmlElement(name="geo-location")
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
	
	/**
	 * Places correct instance to variable during unmarshal
	 * @param u
	 * @param parent
	 */
	protected void afterUnmarshal(Unmarshaller u, Object parent) {
		viewer = (Viewer)parent;
	}
}
