
package com.aneoun.bank.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aneoun.bank.domain.User;
import com.aneoun.bank.repositories.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Service
@Transactional
public class UserService {

	//Static variables--------------------------------------------------------------

	private static User		user;

	//Repository--------------------------------------------------------------------

	@Autowired
	private UserRepository	userRepository;


	//Methods-----------------------------------------------------------------------

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findUser() {
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		final DatabaseReference ref = database.getReference("server/saving-data/fireblog/users");

		ref.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(final DataSnapshot dataSnapshot) {
				final User user = new User(dataSnapshot.getValue(User.class));
				UserService.user = user;
			}

			@Override
			public void onCancelled(final DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});

		return UserService.user;

	}
	public User save(final User user) {
		User saved = new User();

		if (user != null) {
			saved = this.userRepository.save(user);
			this.saveUserFirebase(saved);
		}

		return saved;
	}

	//OtherMethods-------------------------------------------------------------------

	private void saveUserFirebase(final User user) {
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		final DatabaseReference ref = database.getReference("server/saving-data/fireblog");

		final DatabaseReference usersRef = ref.child("users");

		final Map<String, User> users = new HashMap<>();
		users.put(String.valueOf(user.getId()), new User(user.getName(), user.getSurname(), user.getBalance()));

		usersRef.setValueAsync(users);
	}
}
