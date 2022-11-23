package com.SpringSecurityJwt.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringSecurityJwt.exceptions.UserNameNotFoundException;
import com.SpringSecurityJwt.exceptions.UserNotFoundException;
import com.SpringSecurityJwt.model.User;
import com.SpringSecurityJwt.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByUsername(username);
		
		if(user == null)
		{
			throw new UserNameNotFoundException("User Does Not Exist");
		}
		
		return new UserDetail(user);
	}

	public User save(User user) {
		
		return repository.save(user);
	}

	public User findById(Long user_id) {
		
		Optional<User> user = repository.findById(user_id);
		
		if(user.isEmpty())
		{
			throw new UserNotFoundException("No Users Exist for given ID");
		}
		
		 return user.get();
	}

	public User findByUsername(String username) {
		
		return repository.findByUsername(username);
	}

	public User findByPhno( String phno) {
		
		return repository.findByPhno(phno);
	}


	public void deleteById(Long user_id) {
		
		repository.deleteById(user_id);
	}

	

}
