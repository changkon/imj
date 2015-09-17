package changkon.imj.domain;

/**
 * Movie viewer subscribe to movie
 * @author Chang Kon Han
 */
public interface Subscriber {
	void notification();
	void subscribe(Movie movie);
	void unsubscribe(Movie movie);
}
