package com.osman.service;

import java.util.List;

import com.osman.dao.*;
import com.osman.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public User create(User user) {
		userRepository.addIssuer(user);
		User createduser=read(user);
		return createduser;
	}
	
	public User read(User user) {
		User newuser=userRepository.getIssuerByTicker(user.getId());
		return newuser;
	}
	
	public List<User> readAll() {
		return userRepository.getAllIssuers();
	}
	
	public User update(User user) {
		userRepository.updateIssuer(user.getId(), user);
		User newuser=read(user);
		return newuser;
	}
	
	public Boolean delete(User user) {
		userRepository.deleteIssuer(user.getId());
		User deleteduser=read(user);
		if(deleteduser==null){return true;}else{return false;}
	}
}
