package com.Ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecom.Model.user;
import com.Ecom.repo.userRepo;

@Service
public class UserService {

	@Autowired
	private userRepo repo;
	public user adduser(user user) {
		// TODO Auto-generated method stub
		return repo.save(user);
	}

}
