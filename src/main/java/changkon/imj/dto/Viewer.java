package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import changkon.imj.domain.Person;

/**
 * Movie viewer. Viewer is entity which watches movie.
 * 
 * This is DTO implementation. Contains less information to be included in XML to reduce
 * bandwidth.
 * @author Chang Kon Han
 */
@XmlRootElement(name="viewer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Viewer extends Person {
	
	/**
	 * Default constructor. Javabean convention
	 */
	public Viewer() {
		super();
	}
}
