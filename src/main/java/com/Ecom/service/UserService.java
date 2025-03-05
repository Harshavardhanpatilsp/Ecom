package com.Ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Ecom.Model.User;
import com.Ecom.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

	//@Autowired
	private UserRepo repo;
	
	public UserService(UserRepo repo) {
		this.repo = repo;
	}
	public User adduser(User user) {
		// TODO Auto-generated method stub
		return repo.save(user);
	}
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        return repo.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	    }
	}
