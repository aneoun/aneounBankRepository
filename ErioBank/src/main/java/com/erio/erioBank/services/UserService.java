
package com.erio.erioBank.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erio.erioBank.domain.User;
import com.erio.erioBank.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	//Repository--------------------------------------------------------------------

	@Autowired
	private UserRepository	userRepository;


	//Methods-----------------------------------------------------------------------

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findUser() {
		return this.findAll().get(0);

	}
	public User save(final User user) {
		User saved = new User();

		if (user != null)
			saved = this.userRepository.save(user);

		return saved;
	}

	//OtherMethods-------------------------------------------------------------------

}
