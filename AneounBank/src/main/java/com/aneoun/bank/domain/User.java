
package com.aneoun.bank.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(final User user) {
		final User userU = new User();
		userU.setBalance(user.getBalance());
		userU.setId(user.getId());
		userU.setName(user.getName());
		userU.setSurname(user.getSurname());
		userU.setVersion(user.getVersion());
	}

	public User(final String name, final String surname, final double balance) {
		this.name = name;
		this.surname = surname;
		this.balance = balance;

	}


	//Aributtes-----------------------------------------

	private String	name;
	private String	surname;
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

}
