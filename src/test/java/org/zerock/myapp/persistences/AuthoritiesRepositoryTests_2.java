package org.zerock.myapp.persistences;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.myapp.domain.Authorities;
import org.zerock.myapp.persistence.AuthoritiesRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@AutoConfigureMockMvc
@SpringBootTest(
	webEnvironment = WebEnvironment.MOCK,
//	webEnvironment = WebEnvironment.DEFINED_PORT,
	
//	properties = "spring.jpa.hibernate.ddl-auto=create"
	properties = "spring.jpa.hibernate.ddl-auto=update"
)
public class AuthoritiesRepositoryTests_2 {
	@Autowired(required = true)
	private AuthoritiesRepository authoritiesRepo;
	
	private PasswordEncoder bcryptEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		assertNotNull(this.authoritiesRepo);
		log.info("  + this.authoritiesRepo: {}", this.authoritiesRepo);
		log.info("  + this.bcryptEncoder: {}", this.bcryptEncoder);
	} // beforeAll
	
//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("prepareData")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void prepareData() {
		log.trace("prepareData() invoked.");
		
		String username = "", authorityUser = "USER", authorityAdmin = "ADMIN";
		
		// ---------
		username = "Yoon";
		
		Authorities transientYoonWithUser = new Authorities();
		transientYoonWithUser.setUsername(username);
		transientYoonWithUser.setAuthority(authorityUser);
		
		this.authoritiesRepo.save(transientYoonWithUser);
		
		// ---------
		username = "Yoon";
		
		Authorities transientYoonWithAdmin = new Authorities();
		transientYoonWithAdmin.setUsername(username);
		transientYoonWithAdmin.setAuthority(authorityAdmin);
		
		this.authoritiesRepo.save(transientYoonWithAdmin);
		
		// ---------
		username = "Trinity";
		
		Authorities transientTrinityWithUser = new Authorities();
		transientTrinityWithUser.setUsername(username);
		transientTrinityWithUser.setAuthority(authorityUser);
		
		this.authoritiesRepo.save(transientTrinityWithUser);
		
		// ---------
		username = "Pyramid";
		
		Authorities transientPyramidWithAdmin = new Authorities();
		transientPyramidWithAdmin.setUsername(username);
		transientPyramidWithAdmin.setAuthority(authorityAdmin);
		
		this.authoritiesRepo.save(transientPyramidWithAdmin);
	} // prepareData
	
	
} // end class
