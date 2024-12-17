package com.jts.Movie.config;

public class SecurityConfiguration {
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(c -> c.disable())
				.authorizeHttpRequests(req ->
				req.requestMatchers("/User/**").permitAll()
				.requestMatchers("/Movie/**").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers("/show/**").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers("/theater/**").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers("/ticket/**").hasAnyAuthority("ROLE_USER")
				.anyRequest().authenticated())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsservice(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder();
	return new BCryptPasswordEncoder();
}

@Bean
AuthenticationManager autheticationManager(AuthenticationConfiguration config) throws Exception {
	return config.getAuthenticationManager();
}
}