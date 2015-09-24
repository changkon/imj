package changkon.imj.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data transmission object for storing information about movie cast
 * @author Chang Kon Han
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieCast {

	@XmlElementWrapper(name="cast")
	@XmlElement(name="cast-member")
	private List<String> cast;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public MovieCast() {}

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
	
}
