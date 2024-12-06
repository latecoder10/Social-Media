package com.tap.social.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {

	// Define the SecurityFilterChain bean for configuring security settings
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Configure session management to be stateless
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Define authorization rules for incoming requests
				.authorizeHttpRequests(auth -> auth
						// Require authentication for requests matching this pattern
						.requestMatchers("/api/**").authenticated()
						// Permit all other requests
						.anyRequest().permitAll())
				.addFilterBefore(new JwtValidator(), UsernamePasswordAuthenticationFilter.class)
				// Disable CSRF protection since we're using stateless sessions and enable CORS
				.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()));

		return http.build(); // Build the security filter chain
	}

	// Define a CorsConfigurationSource bean for CORS settings
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Removed trailing slash
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Specify allowed
																									// methods
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Specify allowed headers
		configuration.setAllowCredentials(true);
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// Password encoder bean for encoding passwords
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
