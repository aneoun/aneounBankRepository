
package com.aneoun.bank.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aneoun.bank.domain.User;
import com.aneoun.bank.services.UserService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService	userService;


	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> getUser() {
		final User user = this.userService.findUser();
		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> addUser(@RequestBody @Valid final User user, final BindingResult bindingResult, final UriComponentsBuilder ucBuilder) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		this.userService.save(user);
		headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody @Valid final User user, final BindingResult bindingResult, final UriComponentsBuilder ucBuilder) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<User>(headers, HttpStatus.BAD_REQUEST);
		}
		final User currentUser = this.userService.findUser();
		if (currentUser == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		currentUser.setName(user.getName());
		currentUser.setSurname(user.getSurname());
		currentUser.setBalance(user.getBalance());
		this.userService.save(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.NO_CONTENT);
	}

}
