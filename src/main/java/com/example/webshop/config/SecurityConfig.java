package com.example.webshop.config;

import com.example.webshop.repository.UserRepository;
import com.example.webshop.security.JwtAuthenticationEntryPoint;
import com.example.webshop.security.JwtAuthenticationFilter;
import com.example.webshop.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig{
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(POST, "/user/login", "/user/register").permitAll()
				.requestMatchers(GET, "/user/data").hasAnyAuthority("CLIENT", "EMPLOYER", "MANAGER")
				.requestMatchers(POST, "/user/update").hasAnyAuthority("CLIENT", "EMPLOYER", "MANAGER")
				.requestMatchers(GET, "/products/category", "/products/category/{id}").permitAll()
				.requestMatchers(POST, "/products/category").hasAnyAuthority("EMPLOYER", "MANAGER")
				.requestMatchers(PUT, "/products/category").hasAnyAuthority("EMPLOYER", "MANAGER")
				.requestMatchers(DELETE, "/products/category/{id}").hasAnyAuthority("EMPLOYER", "MANAGER")
				.anyRequest().authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint))
		;
		return http.build();
	}

	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(){
		return new JwtAuthenticationFilter(jwtTokenProvider, userRepository);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
		throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}