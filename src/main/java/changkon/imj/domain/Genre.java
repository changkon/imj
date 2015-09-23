package changkon.imj.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enum representing common movie genres
 * @author Chang Kon Han
 */
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
}