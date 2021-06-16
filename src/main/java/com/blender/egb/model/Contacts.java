package com.blender.egb.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.io.Serializable;

@Data
@Embeddable
public class Contacts implements Serializable {

	@Basic(fetch = FetchType.LAZY)
	private String country, state, city, address, zipCode, phoneNumber, email, motherName, fatherName, motherPhoneNumber, fatherPhoneNumber;

}
