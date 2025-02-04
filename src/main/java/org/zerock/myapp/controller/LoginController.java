package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@NoArgsConstructor

@RequestMapping("/security/")
@Controller
public class LoginController {
	
	@GetMapping("/customLogin")
	void customLogin() {
		log.trace("customLogin() invoked.");
		
	} // customLogin
	
	@GetMapping("/loginFailed")
	void loginFailed() {
		log.trace("loginFailed() invoked.");
		
	} // loginFailed
	
	@GetMapping("/403")
	void accessDenied() {
		log.trace("accessDenied() invoked.");
		
	} // accessDenied
} // end class
