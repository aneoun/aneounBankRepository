
package com.aneoun.bank.rest;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aneoun.bank.domain.Register;
import com.aneoun.bank.services.RegisterService;

@RestController
@RequestMapping("/api/registers")
public class RegisterRestController {

	//Service----------------------------------------------------------------------

	@Autowired
	private RegisterService	registerService;


	//Mthods-----------------------------------------------------------------------

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Register>> getRegisters() {
		final List<Register> registers = this.registerService.findAll();
		if (registers.isEmpty())
			return new ResponseEntity<List<Register>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Register>>(registers, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Register> addRegister(@RequestBody @Valid final Register register, final BindingResult bindingResult, final UriComponentsBuilder ucBuilder) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (register == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Register>(headers, HttpStatus.BAD_REQUEST);
		}
		this.registerService.save(register);
		headers.setLocation(ucBuilder.path("/api/registers/{id}").buildAndExpand(register.getId()).toUri());
		return new ResponseEntity<Register>(register, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{registerId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Register> updateRegister(@PathVariable("registerId") final int registerId, @RequestBody @Valid final Register register, final BindingResult bindingResult) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (register == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Register>(headers, HttpStatus.BAD_REQUEST);
		}
		final Register currentRegister = this.registerService.findOne(registerId);
		if (currentRegister == null)
			return new ResponseEntity<Register>(HttpStatus.NOT_FOUND);

		currentRegister.setAmount(register.getAmount());
		currentRegister.setMoment(register.getMoment());
		currentRegister.setStatus(register.getStatus());
		currentRegister.setUser(register.getUser());

		this.registerService.save(currentRegister);
		return new ResponseEntity<Register>(currentRegister, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/{registerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteRegister(@PathVariable("registerId") final int registerId) {
		final Register register = this.registerService.findOne(registerId);
		if (register == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		this.registerService.delete(register);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
