package changkon.imj.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Geographic location
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeoLocation {
	
	@XmlElement
	private double latitude;
	
	@XmlElement
	private double longitude;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public GeoLocation() {}
	
	/**
	 * GeoLocation constructor with initialization values for variable
	 * @param latitude
	 * @param longitude
	 */
	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	/**
	 * @return Latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Set latitude
	 * @param latitude
	 */

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return Longitude
	 */

	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Sets longitude
	 * @param longitude
	 */

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
