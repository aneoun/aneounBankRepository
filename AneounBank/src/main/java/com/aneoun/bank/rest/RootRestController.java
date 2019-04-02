
package com.aneoun.bank.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/")
public class RootRestController {

	@RequestMapping(value = "/")
	public void redirectToSwagger(final HttpServletResponse response) throws IOException {
		response.sendRedirect("/api/registers");
	}

}
