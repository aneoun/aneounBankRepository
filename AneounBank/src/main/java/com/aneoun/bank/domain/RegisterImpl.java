
package com.aneoun.bank.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class RegisterImpl extends DomainEntity implements Register {

	//Atributtes---------------------------------------------------

	private double	ammount;
	private Date	moment;
	private String	status;


	@Min(0)
	public double getAmmount() {
		return this.ammount;
	}

	public void setAmmount(final double ammount) {
		this.ammount = ammount;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^INCOME$|^OUTCOME$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}


	//Relationship---------------------------------------------------------------------

	private User	user;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	//OtherMethods---------------------------------------------------------------------

	@Override
	public double incomming(final double value) {
		return this.user.getBalance() + value;
	}

	@Override
	public double outcomming(final double value) {
		return this.user.getBalance() - value;
	}

}
