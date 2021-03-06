package com.blender.egb.configuration;

import com.blender.egb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;


	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				/*.requiresChannel()
				.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
				.requiresSecure()
				.and()*/
				.authorizeRequests()
					.antMatchers("/login", "/registration").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/**").hasAnyRole("TEACHER", "ADMIN")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/success")
					.permitAll()
				.and()
					.logout()
					.permitAll()
					.logoutSuccessUrl("/login")
				.and()
					.exceptionHandling().accessDeniedPage("/error");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/css/**", "/error");
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
	}
}
