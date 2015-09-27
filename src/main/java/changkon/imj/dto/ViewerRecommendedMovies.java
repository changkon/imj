package changkon.imj.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import changkon.imj.dto.Movie;

@XmlRootElement(name="recommendations")
public class ViewerRecommendedMovies {

	@XmlElementWrapper(name="recommended")
	private Set<Movie> recommendedMovies;
	
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
