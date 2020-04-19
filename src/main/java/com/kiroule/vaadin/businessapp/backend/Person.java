package com.kiroule.vaadin.businessapp.backend;

import java.time.LocalDate;

public class Person {

	public enum Role {
		DESIGNER, DEVELOPER, MANAGER, TRADER, PAYMENT_HANDLER, ACCOUNTANT
	}

	private Long id;
	private String firstName;
	private String lastName;
	private Role role;
	private String email;
	private boolean randomBoolean;
	private int randomInteger;
	private LocalDate lastModified;

	public Person() {

	}

	public Person(Long id, String firstName, String lastName, Role role,
	              String email, boolean randomBoolean, int randomInteger,
	              LocalDate lastModified) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.email = email;
		this.randomBoolean = randomBoolean;
		this.randomInteger = randomInteger;
		this.lastModified = lastModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getInitials() {
		return (firstName.substring(0, 1) + lastName.substring(0, 1))
				.toUpperCase();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getRandomBoolean() {
		return randomBoolean;
	}

	public void setRandomBoolean(boolean randomBoolean) {
		this.randomBoolean = randomBoolean;
	}

	public int getRandomInteger() {
		return randomInteger;
	}

	public void setRandomInteger(int randomInteger) {
		this.randomInteger = randomInteger;
	}

	public LocalDate getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDate lastModified) {
		this.lastModified = lastModified;
	}

}
