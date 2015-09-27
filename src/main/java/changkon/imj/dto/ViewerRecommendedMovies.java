package changkon.imj.dto;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="recommendations")
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewerRecommendedMovies {

	@XmlElement(name="recommended")
	private Set<Movie> recommendedMovies = new HashSet<Movie>();
	
	/**
	 * Default constructor. Javabean convention
	 */
	public ViewerRecommendedMovies() {}

	public Set<Movie> getRecommendedMovies() {
		return recommendedMovies;
	}

	public void setRecommendedMovies(Set<Movie> recommendedMovies) {
		this.recommendedMovies = recommendedMovies;
	}
}
