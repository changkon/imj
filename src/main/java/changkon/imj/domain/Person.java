package changkon.imj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class which defines characteristic of Person entity in IMJ
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="PERSON")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Person {
	
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@XmlAttribute(name="id")
	@Column(name="ID")
	private Long id;
	
	@Column(name="FIRSTNAME")
	@XmlElement(name="first_name")
	private String firstName;
	
	@Column(name="LASTNAME")
	@XmlElement(name="last_name")
	private String lastName;
	
	@Column(name="AGE")
	@XmlElement
	private int age;
	
	@XmlElement
	@Enumerated(EnumType.STRING)
	@Column(name="GENDER")
	private Gender gender;
	
	@Column(name="COUNTRY")
	@XmlElement
	private String country;
	
	/**
	 * Empty/Default constructor. Follows JavaBean convention
	 */
	public Person() {}
	
	/**
	 * Person constructor with initialization values for variables
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param gender
	 * @param country
	 */
	public Person(Long id, String firstName, String lastName, int age, Gender gender, String country) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.country = country;
	}
	
	/**
	 * @return Id of person
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets id of person
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return First name of person
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets first name of Person
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return Last name of person
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return Age of person
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Sets age of person
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * @return Gender of person
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Sets gender of person
	 * @param gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * Sets last name of Person
	 * @param firstName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return Country of origin of person
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets country of origin of Person
	 * @param firstName
	 */
	public void setCountry(String country) {
		this.country = country;
	}
}
