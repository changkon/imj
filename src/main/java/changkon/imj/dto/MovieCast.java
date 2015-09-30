package changkon.imj.dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data transmission object for storing information about movie cast
 * @author Chang Kon Han
 */

@XmlRootElement(name="cast")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieCast {

	@XmlElement(name="cast_member")
	private Collection<String> cast;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public MovieCast() {}

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
	
}
