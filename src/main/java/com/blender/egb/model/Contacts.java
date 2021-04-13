package com.blender.egb.model;

import javax.persistence.Embeddable;


@Embeddable
public class Contacts {

	private String country;
	private String state;
	private String city;
	private String address;
	private String zipCode;
	private String phoneNumber;
	private String email;
	private String motherName;
	private String fatherName;
	private String motherPhoneNumber;
	private String fatherPhoneNumber;

	public Contacts() {
	}

	public Contacts(String country, String state,
	                String city, String address, String zipCode,
	                String phoneNumber, String email, String motherName,
	                String fatherName, String motherPhoneNumber, String fatherPhoneNumber) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.address = address;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.motherName = motherName;
		this.fatherName = fatherName;
		this.motherPhoneNumber = motherPhoneNumber;
		this.fatherPhoneNumber = fatherPhoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherPhoneNumber() {
		return motherPhoneNumber;
	}

	public void setMotherPhoneNumber(String motherPhoneNumber) {
		this.motherPhoneNumber = motherPhoneNumber;
	}

	public String getFatherPhoneNumber() {
		return fatherPhoneNumber;
	}

	public void setFatherPhoneNumber(String fatherPhoneNumber) {
		this.fatherPhoneNumber = fatherPhoneNumber;
	}

	@Override
	public String toString() {
		return "Contacts{" +
				", country='" + country + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", address='" + address + '\'' +
				", zipCode='" + zipCode + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", motherName='" + motherName + '\'' +
				", fatherName='" + fatherName + '\'' +
				", motherPhoneNumber='" + motherPhoneNumber + '\'' +
				", fatherPhoneNumber='" + fatherPhoneNumber + '\'' +
				'}';
	}
}
