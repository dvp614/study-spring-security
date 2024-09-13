package org.zerock.myapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@NoArgsConstructor

@Configuration("secutiryConfig")
public class SecurityConfig {
	@Autowired private PasswordEncoder encoder;
	@Autowired private DataSource dataSource;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		log.trace("webSecurityCustomizer() invoked.");
		
		
//		return web -> web.debug(false).ignoring().anyRequest();
		return web -> web.debug(false);
	} // webSecurityCustomizer
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.trace("securityFilterChain({}) invoked.", http);
		
//		http.authorizeHttpRequests(
//			customizer -> customizer.requestMatchers("/security/root").permitAll()
//									.requestMatchers("/security/member").fullyAuthenticated()
//									.requestMatchers("/security/manager").hasRole("MANAGER")
//									.requestMatchers("/security/admin").hasRole("ADMIN")
//		); // .authorizeHttpRequests
		
		
//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/login").permitAll());
//		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/loginFailed").permitAll());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/root").permitAll());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/member").authenticated());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/manager").hasAnyRole("ADMIN","MANAGER"));
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/admin").hasAuthority("ROLE_ADMIN"));
		
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/403").permitAll());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/logout").authenticated());
		http.authorizeHttpRequests(customizer -> customizer.requestMatchers("/security/customLogin").permitAll());
		
//		http.formLogin(Customizer.withDefaults());
		
		http.formLogin(
			customizer -> 
				customizer
					.loginPage("/security/customLogin")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/security/member", false)
					.failureUrl("/security/customLogin")
//					.failureUrl("/security/loginFailed")
		); // .formLogin
		
		http.csrf(customizer -> customizer.disable());
		
//		http.logout(Customizer.withDefaults());
		http.logout(
			customizer ->
				customizer
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.logoutUrl("/logout")
					.logoutSuccessUrl("/security/customLogin")
		); // .logout
		
		http.exceptionHandling(customizer -> customizer.accessDeniedPage("/security/403"));
		
		return http.build();
	} // securityFilterChain
	
	@Bean
	UserDetailsService userDetailsService() {
		log.trace("userDetailsService() invoked.");
		
		UserDetails user = 
				User.withUsername("user")
//					.password("{noop}1234")
					.password("1234")
					.roles("USER")
					.disabled(false)
					.passwordEncoder(this.encoder::encode)
					.accountLocked(false)
					.accountExpired(false)
					.credentialsExpired(false)
					.build();

		UserDetails manager = 
				User.withUsername("manager")
	//				.password("{noop}1234")
					.password("1234")
					.roles("MANAGER")
					.disabled(false)
					.passwordEncoder(this.encoder::encode)
					.accountLocked(false)
					.accountExpired(false)
					.credentialsExpired(false)
					.build();
		
		UserDetails admin = 
				User.withUsername("admin")
	//				.password("{noop}1234")
					.password("1234")
					.roles("ADMIN")
					.disabled(false)
					.passwordEncoder(this.encoder::encode)
					.accountLocked(false)
					.accountExpired(false)
					.credentialsExpired(false)
	//				.authorities("ROLE_ADMIN")
					.build();
		
		return new InMemoryUserDetailsManager(user, manager, admin);
	} // userDetailsService
	
	@Autowired
	void authenticate(AuthenticationManagerBuilder auth) throws Exception{
		log.trace("authenticate ({}) invoked.", auth);
		
		final String userQuery = "SELECT username, password, enabled FROM users WHERE username = ?";
		final String authorityQuery = "SELECT username, authority FROM Authorities WHERE username = ?";
		
		auth.jdbcAuthentication()
			.dataSource(this.dataSource)
			.passwordEncoder(encoder)
			.rolePrefix("ROLE_")
			.usersByUsernameQuery(userQuery)
			.authoritiesByUsernameQuery(authorityQuery);
		
		final String userQuery2 = "SELECT name as username, '{noop}'||password, true FROM member WHERE name = ?";
		final String authorityQuery2 = "SELECT name as username, upper(role) as authority FROM member WHERE name = ?";
		
		auth.jdbcAuthentication()
			.dataSource(this.dataSource)
//			.passwordEncoder(encoder)
			.rolePrefix("ROLE_")
			.usersByUsernameQuery(userQuery2)
			.authoritiesByUsernameQuery(authorityQuery2);
		
	} // authenticate
	
} // end class
