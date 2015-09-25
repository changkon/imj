package changkon.imj.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import changkon.imj.jaxb.ReleaseAdapter;

/**
 * Data transmission object for sending/receiving information about movie release dates
 * @author Chang Kon Han
 */

@XmlRootElement(name="release-info")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieReleaseDates {
	
	@XmlJavaTypeAdapter(ReleaseAdapter.class)
	private Map<String, DateTime> releases;
	
	public MovieReleaseDates() {}

	public Map<String, DateTime> getReleases() {
		return releases;
	}

	public void setReleases(Map<String, DateTime> releases) {
		this.releases = releases;
	}
	
}
