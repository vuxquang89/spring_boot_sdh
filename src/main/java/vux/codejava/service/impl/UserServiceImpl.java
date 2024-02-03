package vux.codejava.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.convert.UserConvert;
import vux.codejava.entity.User;
import vux.codejava.repository.UserRepository;
import vux.codejava.response.UserResponse;
import vux.codejava.service.UserServices;

@Service
public class UserServiceImpl implements UserServices{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserConvert userConvert;
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findByUserId(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public List<UserResponse> findUser() {
		List<User> users = userRepository.findAllUser();
		List<UserResponse> responses = new ArrayList<>();
		if (users.size() > 0) {
			for(User user : users) {
				responses.add(userConvert.response(user));
			}
		}
		return responses;
	}

}
