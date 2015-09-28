package changkon.imj.recommend;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import changkon.imj.domain.Genre;
import changkon.imj.domain.Movie;

/**
 * Returns a BIASED recommended value based on movie genres
 * @author Chang Kon Han
 */
public class RatingGenerator {

	private static Map<Criteria, Double> ratingLookup;
	
	static {
		ratingLookup = new HashMap<Criteria, Double>();
		
		// Action
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.ACTION), 5.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.ADVENTURE), 4.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.COMEDY), 2.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.CRIME), 4.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.MYSTERY), 2.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.SCIENCEFICTION), 1.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.THRILLER), 3.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.ACTION, Genre.BIOGRAPHY), 0.0);
		
		// Adventure
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.ACTION), 4.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.ADVENTURE), 5.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.COMEDY), 2.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.CRIME), 4.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.FANTASY), 1.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.SCIENCEFICTION), 1.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.THRILLER), 3.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.ANIMATION), 1.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.DRAMAFILM), 1.0);
		ratingLookup.put(new Criteria(Genre.ADVENTURE, Genre.BIOGRAPHY), 0.0);
		
		// Comedy
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.ACTION), 2.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.ADVENTURE), 2.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.COMEDY), 5.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.ROMANCE), 3.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.THRILLER), 1.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.ANIMATION), 1.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.COMEDY, Genre.BIOGRAPHY), 0.0);
		
		// Crime
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.ACTION), 4.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.ADVENTURE), 4.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.CRIME), 5.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.MYSTERY), 4.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.THRILLER), 3.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.CRIME, Genre.BIOGRAPHY), 0.0);
		
		// Fantasy
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.ADVENTURE), 1.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.FANTASY), 5.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.SCIENCEFICTION), 3.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.FANTASY, Genre.BIOGRAPHY), 0.0);
		
		// Horror
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.ADVENTURE), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.HORROR), 5.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.MYSTERY), 3.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.THRILLER), 3.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.HORROR, Genre.BIOGRAPHY), 0.0);
		
		// Mystery
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.ACTION), 2.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.ADVENTURE), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.CRIME), 4.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.HORROR), 3.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.MYSTERY), 5.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.THRILLER), 34.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.MYSTERY, Genre.BIOGRAPHY), 0.0);
		
		// Romance
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.ADVENTURE), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.COMEDY), 3.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.ROMANCE), 5.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.DRAMAFILM), 3.0);
		ratingLookup.put(new Criteria(Genre.ROMANCE, Genre.BIOGRAPHY), 3.0);
		
		// Science Fiction
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.ACTION), 1.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.ADVENTURE), 1.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.FANTASY), 3.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.SCIENCEFICTION), 5.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.ANIMATION), 2.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.SCIENCEFICTION, Genre.BIOGRAPHY), 0.0);
		
		// Thriller
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.ACTION), 3.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.ADVENTURE), 3.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.COMEDY), 1.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.CRIME), 3.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.HORROR), 3.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.MYSTERY), 4.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.THRILLER), 5.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.THRILLER, Genre.BIOGRAPHY), 0.0);
		
		// Animation
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.ADVENTURE), 1.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.COMEDY), 1.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.ROMANCE), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.SCIENCEFICTION), 2.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.ANIMATION), 5.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.DRAMAFILM), 0.0);
		ratingLookup.put(new Criteria(Genre.ANIMATION, Genre.BIOGRAPHY), 0.0);
		
		// Dramafilm
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.ADVENTURE), 1.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.ROMANCE), 3.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.DRAMAFILM), 5.0);
		ratingLookup.put(new Criteria(Genre.DRAMAFILM, Genre.BIOGRAPHY), 4.0);
		
		// Biography
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.ACTION), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.ADVENTURE), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.COMEDY), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.CRIME), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.FANTASY), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.HORROR), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.MYSTERY), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.ROMANCE), 3.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.SCIENCEFICTION), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.THRILLER), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.ANIMATION), 0.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.DRAMAFILM), 4.0);
		ratingLookup.put(new Criteria(Genre.BIOGRAPHY, Genre.BIOGRAPHY), 5.0);
		
	}
	
	/**
	 * Prefers movies which isn't released yet since higher chance it hasn't seen it
	 * @param date
	 * @return
	 */
	private static double getDatePref(DateTime date) {
		DateTime now = DateTime.now();
		
		if (date.isAfter(now)) {
			return 1;
		} else {
			return 0.5;
		}
	}
	
	/**
	 * Returns normalised rating
	 * @param value
	 * @return
	 */
	
	private static double clip(double value) {
		if (value < 0) {
			return 0;
		} else if (value > 1) {
			return 1;
		} else {
			return value;
		}
	}
	
	/**
	 * Round to 2 d.p
	 * @param value
	 * @return
	 */
	private static double round(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
	
	public static double getRating(Genre preferredGenre, Genre movieGenre, DateTime release) {
		double rating = 0;
		
		double genreMatchRating = ratingLookup.get(new Criteria(preferredGenre, movieGenre));
		double dateRating = getDatePref(release);
		
		rating = round(clip(genreMatchRating * dateRating));
		
		return rating;
	}
	
}
