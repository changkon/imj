package changkon.imj.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;

import changkon.imj.dto.MovieReleaseDateEntry;
import changkon.imj.dto.MovieReleaseDatesType;

/**
 * Responsible for converting Map of release dates into MovieReleaseDatesType instance and vice versa
 * @author Chang Kon Han
 */

public class ReleaseAdapter extends XmlAdapter<MovieReleaseDatesType, Map<String, DateTime>> {

	@Override
	public MovieReleaseDatesType marshal(Map<String, DateTime> releases) throws Exception {
		MovieReleaseDatesType movieReleaseDates = new MovieReleaseDatesType();
		
		List<MovieReleaseDateEntry> entries = new ArrayList<MovieReleaseDateEntry>();
		
		for (Map.Entry<String, DateTime> e : releases.entrySet()) {
			MovieReleaseDateEntry releaseDateEntry = new MovieReleaseDateEntry();
			releaseDateEntry.setCountry(e.getKey());
			releaseDateEntry.setDate(e.getValue());
			entries.add(releaseDateEntry);
		}
		
		movieReleaseDates.setReleases(entries);
		
		return movieReleaseDates;
	}

	@Override
	public Map<String, DateTime> unmarshal(MovieReleaseDatesType releases) throws Exception {
		Map<String, DateTime> releaseMap = new HashMap<String, DateTime>();
		
		List<MovieReleaseDateEntry> entries = releases.getReleases();
		
		for (MovieReleaseDateEntry entry : entries) {
			releaseMap.put(entry.getCountry(), entry.getDate());
		}
		
		return releaseMap;
	}

}
