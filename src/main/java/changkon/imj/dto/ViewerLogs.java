package changkon.imj.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import changkon.imj.dto.Log;

@XmlRootElement(name="logs")
public class ViewerLogs {

	@XmlElement(name="log")
	public Set<Log> movieLog;
	
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
