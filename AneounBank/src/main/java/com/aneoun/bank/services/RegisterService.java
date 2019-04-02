
package com.aneoun.bank.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aneoun.bank.domain.RegisterImpl;
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

	public RegisterImpl create() {
		final RegisterImpl registerImpl = new RegisterImpl();

		registerImpl.setMoment(new Date(System.currentTimeMillis() - 1000));
		registerImpl.setUser(this.userService.findUser());

		return registerImpl;
	}

	public List<RegisterImpl> findAll() {
		return this.registerRepository.findAll();
	}

	public RegisterImpl save(final RegisterImpl registerImpl) {
		RegisterImpl saved = null;

		if (registerImpl != null) {
			saved = this.registerRepository.save(registerImpl);
			this.updateBalance(saved);
		}

		return saved;
	}

	public void delete(final RegisterImpl registerImpl) {
		this.registerRepository.delete(registerImpl);
	}

	public RegisterImpl findOne(final Integer registerId) {
		RegisterImpl impl = null;
		for (final RegisterImpl registerImpl : this.findAll())
			if (registerImpl.getId() == registerId) {
				impl = registerImpl;
				break;
			}
		return impl;
	}
	//Other---------------------------------------------------------------------------

	public void updateBalance(final RegisterImpl registerImpl) {
		final User user = registerImpl.getUser();
		double balance = 0.0;

		if (registerImpl.getStatus() == "INCOME")
			balance = registerImpl.incomming(registerImpl.getAmmount());
		else
			balance = registerImpl.outcomming(registerImpl.getAmmount());

		user.setBalance(balance);
		this.userService.save(user);
	}

}
