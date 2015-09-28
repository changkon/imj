package changkon.imj.recommend;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import changkon.imj.domain.Genre;

public class Criteria {

	private Genre genre1;
	private Genre genre2;
	
	public Criteria(Genre genre1, Genre genre2) {
		this.genre1 = genre1;
		this.genre2 = genre2;
	}
	
	public Genre getGenre1() {
		return genre1;
	}

	public void setGenre1(Genre genre1) {
		this.genre1 = genre1;
	}

	public Genre getGenre2() {
		return genre2;
	}

	public void setGenre2(Genre genre2) {
		this.genre2 = genre2;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
				.append(genre1)
				.append(genre2)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Criteria)) {
			return false;
		} else if (obj == this) {
			return true;
		}
		
		// Cast
		Criteria other = (Criteria)obj;
		return genre1 == other.getGenre1() && genre2 == other.getGenre2();
	}
	
	
	
}
