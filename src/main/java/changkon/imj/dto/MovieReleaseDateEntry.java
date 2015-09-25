package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.joda.time.DateTime;

/**
 * Release entry of movie release info
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieReleaseDateEntry {

	@XmlAttribute(name="key")
	private String country;
	
	@XmlValue
	private DateTime date;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public MovieReleaseDateEntry() {}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
