package changkon.imj.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Wrapper for the Map of Release Date for movie.
 * @author Chang Kon Han
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class MovieReleaseDatesType {

	@XmlElement(name="release")
	private List<MovieReleaseDateEntry> releases;
	
	public MovieReleaseDatesType() {}

	public List<MovieReleaseDateEntry> getReleases() {
		return releases;
	}

	public void setReleases(List<MovieReleaseDateEntry> releases) {
		this.releases = releases;
	}
	
}
