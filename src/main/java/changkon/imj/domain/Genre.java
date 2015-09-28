package changkon.imj.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Enum representing common movie genres
 * @author Chang Kon Han
 */
@XmlRootElement(name="genre")
@XmlType(name="genre")
@XmlEnum
public enum Genre {
	@XmlEnumValue("action")
	ACTION,
	@XmlEnumValue("adventure")
	ADVENTURE,
	@XmlEnumValue("comedy")
	COMEDY,
	@XmlEnumValue("crime")
	CRIME,
	@XmlEnumValue("fantasy")
	FANTASY,
	@XmlEnumValue("horror")
	HORROR,
	@XmlEnumValue("mystery")
	MYSTERY,
	@XmlEnumValue("romance")
	ROMANCE,
	@XmlEnumValue("sciencefiction")
	SCIENCEFICTION,
	@XmlEnumValue("thriller")
	THRILLER,
	@XmlEnumValue("animation")
	ANIMATION,
	@XmlEnumValue("dramafilm")
	DRAMAFILM,
	@XmlEnumValue("biography")
	BIOGRAPHY;
	
//	public static Genre getGenre(String genre) {
//		String lcgenre = genre.toLowerCase();
//		
//		if (genre.equals("action")) {
//			
//		} else if (genre.equals("adventure")) {
//			
//		} else if (genre.equals("comedy")) {
//			
//		} else if (genre.equals("crime")) {
//			
//		} else if (genre.equals("fantasy")) {
//			
//		} else if (genre.equals("horror")) {
//			
//		} else if (genre.equals("mystery")) {
//			
//		} else if (genre.equals("romance")) {
//			
//		} else if (genre.equals("sciencefiction")) {
//			
//		} else if (genre.equals("thriller")) {
//			
//		} else if (genre.equals("animation")) {
//			
//		} else if (genre.equals("dramafilm")) {
//			
//		}
//		
//		return null;
//		
//	}
}