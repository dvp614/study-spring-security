package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Log4j2
@Slf4j
@NoArgsConstructor

@RequestMapping("/security/")
@Controller
public class SecurityController {
	
	@GetMapping("/root")
	void root() {
		log.trace("root() invoked.");
		
	} // root
	
	@GetMapping("/manager")
	void manager() {
		log.trace("manager() invoked.");
		
	} // manager
	
	@GetMapping("/member")
	void member() {
		log.trace("member() invoked.");
		
	} // member
	
	@GetMapping("/admin")
	void admin() {
		log.trace("admin() invoked.");
		
	} // admin
	

} // end class
