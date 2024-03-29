package changkon.imj.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import changkon.imj.dto.Log;

@XmlRootElement(name="logs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewerLogs {

	@XmlElement(name="log")
	private Set<Log> movieLog;
	
	/**
	 * Default constructor. Javabean convention
	 */
	public ViewerLogs() {}

	public Set<Log> getMovieLog() {
		return movieLog;
	}

	public void setMovieLog(Set<Log> movieLog) {
		this.movieLog = movieLog;
	}
	
}
