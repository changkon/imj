package changkon.imj.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class which defines characteristic of Person entity in IMJ
 * @author Chang Kon Han
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Person {
	
	@XmlElement(name="first-name")
	private String firstName;
	
	@XmlElement(name="last-name")
	private String lastName;
	
	@XmlElement
	private int age;
	
	@XmlElement
	private Gender gender;
	
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
	public Person(String firstName, String lastName, int age, Gender gender, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.country = country;
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
