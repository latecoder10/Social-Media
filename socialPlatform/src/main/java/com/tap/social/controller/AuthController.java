package com.tap.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.social.configuration.JwtProvider;
import com.tap.social.dto.UserDTO;
import com.tap.social.models.User;
import com.tap.social.request.LoginRequest;
import com.tap.social.response.AuthResponse;
import com.tap.social.service.CustomerUserDetailsService;
import com.tap.social.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUser(@RequestBody UserDTO userDTO) {
		User newUser = new User();
		newUser.setEmail(userDTO.getEmail());
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		newUser.setGender(userDTO.getGender());
		User createdUser = userService.registerUser(newUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(),
				createdUser.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token, "Register success!");
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED); // Return 201 CREATED
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginResquest) {

		Authentication authentication = authenticate(loginResquest.getEmail(), loginResquest.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token, "Login success !");
		return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED); // Return 202 Accepted

	}

	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username!");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {

			throw new BadCredentialsException("Password not matched !");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
