
package com.aneoun.bank.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aneoun.bank.domain.Register;
import com.aneoun.bank.domain.User;
import com.aneoun.bank.repositories.RegisterRepository;

@Service
@Transactional
public class RegisterService {

	//Repository-------------------------------------------------------------

	@Autowired
	private RegisterRepository	registerRepository;

	//Services---------------------------------------------------------------

	@Autowired
	private UserService			userService;


	//CRUD-------------------------------------------------------------------

	public List<Register> findAll() {
		return this.registerRepository.findAll();
	}

	public Register save(final Register register) {

		register.setUser(this.updateBalance(register));
		final Register saved = this.registerRepository.save(register);

		return saved;
	}

	public void delete(final Register register) {
		this.updateDeleteBalance(register);
		this.registerRepository.delete(register);
	}

	public Register findOne(final Integer registerId) {
		Register register = null;
		for (final Register registerImpl : this.findAll())
			if (registerImpl.getId() == registerId) {
				register = registerImpl;
				break;
			}
		return register;
	}
	//Other---------------------------------------------------------------------------

	public User updateBalance(final Register register) {
		final User user = this.userService.findUser();
		double balance = 0.0;

		if (register.getStatus().equals("OUTCOME"))
			if (register.getAmount() <= user.getBalance())
				balance = this.outcomming(user.getBalance(), register.getAmount());

			else
				balance = this.incomming(user.getBalance(), register.getAmount());

		user.setBalance(balance);
		return this.userService.save(user);
	}

	public void updateDeleteBalance(final Register register) {
		final User user = this.userService.findUser();
		double balance = 0.0;

		if (register.getAmount() <= user.getBalance())
			if (register.getStatus().equals("OUTCOME"))
				balance = this.incomming(user.getBalance(), register.getAmount());
			else
				balance = this.outcomming(user.getBalance(), register.getAmount());

		user.setBalance(balance);
		this.userService.save(user);
	}

	private double incomming(final double balance, final double amount) {
		return balance + amount;
	}

	private double outcomming(final double balance, final double amount) {
		return balance - amount;
	}

}
