
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

import com.aneoun.bank.domain.RegisterImpl;
import com.aneoun.bank.services.RegisterService;

@RestController
@RequestMapping("/api/registers")
public class RegisterRestController {

	//Service----------------------------------------------------------------------

	@Autowired
	private RegisterService	registerService;


	//Mthods-----------------------------------------------------------------------

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<RegisterImpl>> getRegisters() {
		final List<RegisterImpl> registerImpls = this.registerService.findAll();
		if (registerImpls.isEmpty())
			return new ResponseEntity<List<RegisterImpl>>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<RegisterImpl>>(registerImpls, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RegisterImpl> addRegister(@RequestBody @Valid final RegisterImpl registerImpl, final BindingResult bindingResult, final UriComponentsBuilder ucBuilder) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (registerImpl == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<RegisterImpl>(headers, HttpStatus.BAD_REQUEST);
		}
		this.registerService.save(registerImpl);
		headers.setLocation(ucBuilder.path("/api/registers/{id}").buildAndExpand(registerImpl.getId()).toUri());
		return new ResponseEntity<RegisterImpl>(registerImpl, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{registerId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RegisterImpl> updateRegister(@PathVariable("registerId") final int registerId, @RequestBody @Valid final RegisterImpl registerImpl, final BindingResult bindingResult) {
		final BindingErrorsResponse errors = new BindingErrorsResponse();
		final HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (registerImpl == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<RegisterImpl>(headers, HttpStatus.BAD_REQUEST);
		}
		final RegisterImpl currentRegisterImpl = this.registerService.findOne(registerId);
		if (currentRegisterImpl == null)
			return new ResponseEntity<RegisterImpl>(HttpStatus.NOT_FOUND);

		currentRegisterImpl.setAmmount(registerImpl.getAmmount());
		currentRegisterImpl.setMoment(registerImpl.getMoment());
		currentRegisterImpl.setStatus(registerImpl.getStatus());
		currentRegisterImpl.setUser(registerImpl.getUser());

		this.registerService.save(currentRegisterImpl);
		return new ResponseEntity<RegisterImpl>(currentRegisterImpl, HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/{registerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteRegister(@PathVariable("registerId") final int registerId) {
		final RegisterImpl registerImpl = this.registerService.findOne(registerId);
		if (registerImpl == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		this.registerService.delete(registerImpl);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
