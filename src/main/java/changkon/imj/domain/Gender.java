package changkon.imj.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enum for representing Gender
 * @author Chang Kon Han
 *
 */
@XmlType(name="gender")
@XmlEnum
public enum Gender {
	@XmlEnumValue("male")
	MALE,
	@XmlEnumValue("female")
	FEMALE
}
