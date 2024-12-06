package com.tap.social.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tap.social.models.User;
import com.tap.social.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userOptional = userRepository.findByEmail(username);
		
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
		
		User user=userOptional.get();
		
		List<GrantedAuthority> authorities=new ArrayList<>();//used primarily used as role base application like e-comerse site
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
