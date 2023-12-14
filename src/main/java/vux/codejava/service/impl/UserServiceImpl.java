package vux.codejava.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.User;
import vux.codejava.repository.UserRepository;
import vux.codejava.service.UserServices;

@Service
public class UserServiceImpl implements UserServices{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
