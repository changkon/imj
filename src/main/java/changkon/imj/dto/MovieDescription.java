package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name="description")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieDescription {
	
	@JsonProperty("description")
	@XmlValue
	private String description;
	
	public MovieDescription() {}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
