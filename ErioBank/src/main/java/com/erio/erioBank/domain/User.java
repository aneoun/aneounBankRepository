
package com.erio.erioBank.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	//Aributtes-----------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private double	balance;


	@NotBlank
	@Length(max = 25)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@Length(max = 25)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Min(0)
	public double getBalance() {
		return this.balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
