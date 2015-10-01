package changkon.imj.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name="url")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoviePoster {

	@JsonProperty("url")
	@XmlValue
	private String url;
	
	public MoviePoster() {}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
