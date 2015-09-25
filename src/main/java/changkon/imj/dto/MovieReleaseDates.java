package changkon.imj.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name="releases")
public class MovieReleaseDates {

	@XmlElement(name="release")
	private Map<String, DateTime> releases;
	
	public MovieReleaseDates() {}

	public Map<String, DateTime> getReleases() {
		return releases;
	}

	public void setReleases(Map<String, DateTime> releases) {
		this.releases = releases;
	}
	
}
