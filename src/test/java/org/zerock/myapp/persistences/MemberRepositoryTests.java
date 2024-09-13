package org.zerock.myapp.persistences;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.zerock.myapp.domain.Member;
import org.zerock.myapp.persistence.MemberRepository;
import org.zerock.myapp.util.RandomNumberGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@SpringBootTest(
		webEnvironment = WebEnvironment.MOCK, 
//		webEnvironment = WebEnvironment.DEFINED_PORT,
		properties = "spring.jpa.hibernate.ddl-auto=create"
//		properties = "spring.jpa.hibernate.ddl-auto=update"
)

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberRepositoryTests {
	@Autowired(required = true) private MemberRepository memberRepo;

	@BeforeAll
	void beforeAll() {
		log.trace("(1) beforeAll() invoked.");
		log.info("  + this.memberRepo: {}", this.memberRepo);
	} // beforeAll
	
	
//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void prepareData() {
		log.trace("prepareData() invoked.");
		
		// -----------
		// Step1. Prepare Members (Parents)
		// -----------
		final String[] roles = { "user", "admin" };
		final String[] names = { "Yoon", "Trinity", "Pyramid" };
		
		IntStream.rangeClosed(1, 3).forEachOrdered(seq -> {
			log.trace("forEachOrdered({}) invoked.", seq);
			
			// ---
			Member transientMember = new Member();
			
			transientMember.setId(0L + seq);
			transientMember.setPassword("1234");
			transientMember.setName( names[ --seq ] );
			transientMember.setRole(roles[ RandomNumberGenerator.generateOneInt(0, 2) ]);

			// ---
			try { Thread.sleep(1000L * RandomNumberGenerator.generateOneInt(1, 3)); }
			catch (InterruptedException _ignored) {}

			// ---
			this.memberRepo.save(transientMember);
		});		// .forEachOrdered
		
	} // prepareData
	
} // end class


