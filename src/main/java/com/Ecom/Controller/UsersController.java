package com.Ecom.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ecom.Model.user;
import com.Ecom.service.UserService;

@RestController
@CrossOrigin
public class UsersController {
	
	@Autowired
	private UserService service;
	List<user> users = new ArrayList<>(List.of(new user(1,"Harsha","8460"),new user(2,"poorva","8460")));

	
	@GetMapping("/users")
	public List<user> getusers() {
		return users;
	}
	
	@PostMapping("/users/{user}")
	public user adduser(@RequestBody user User) {
		return service.adduser(User);
	}
}
