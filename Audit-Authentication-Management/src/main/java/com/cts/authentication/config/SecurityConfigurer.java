package com.cts.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.authentication.service.ManagerDetailsService;
import com.cts.authentication.util.JwtRequestFilter;


/**
 This class is used for the security configuration. It extends the
 *          class WebSecurityConfigurerAdapter It will take care of the
 *          authentication and authorization based on the user credentials.
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	/**
	 * This is a private field of class {@link ManagerDetailsService}
	 * {@link ManagerDetailsService} class is used to load the user credentials
	 * from the database. Based on that database fetched user credentials
	 * authentication will be performed
	 */
	@Autowired
	private ManagerDetailsService managerDetailsService;
	/**
	 * This is a private field of class {@link JwtRequestFilter} This class extends
	 * {@link OncePerRequestFilter} so every request will first hit this filter
	 */
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		super.configure(auth);
		auth.userDetailsService(managerDetailsService);
	}
	/**
	 * This methods used to override the credentials which spring automatically
	 * generates By using {@link AuthenticationManagerBuilder} object we are
	 * overriding the security credentials with our own given credentials It will
	 * call the userDetailsService method on the
	 * {@link AuthenticationManagerBuilder} class object and this method is present
	 * in class {@link AuthenticationManagerBuilder}
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated()
		.and().exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
	}
	/**
	 * This method is used to inject a {@link AuthenticationManager} type bean We
	 * are annotating this method with @Bean. @Bean annotation tells that a method
	 * produces a bean to be managed by the Spring container.
	 */
	
	@Override
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**", "/v3/api-docs","/v3/api-docs/**" ,"/v2/api-docs","/configuration/ui",
				"/swagger-resources/**","/configuration/security","/swagger-ui/**","/swagger-ui.html","/webjars/**","/auth/swagger");
	}


}