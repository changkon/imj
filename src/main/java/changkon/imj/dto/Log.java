package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import changkon.imj.domain.GeoLocation;

@XmlRootElement(name="log")
@XmlAccessorType(XmlAccessType.FIELD)
public class Log {

	@XmlAttribute(name="id")
	private Long id;
	
	@JsonIgnore
	@XmlTransient
	private Viewer viewer;
	
	@XmlElement
	private Movie movie;
	
	@XmlElement
	private DateTime date;
	
	@XmlElement(name="geo-location")
	private GeoLocation geoLocation;
	
	public Log() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Viewer getViewer() {
		return viewer;
	}

	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public GeoLocation getGeolocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	
}
