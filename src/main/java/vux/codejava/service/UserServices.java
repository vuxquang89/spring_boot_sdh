package vux.codejava.service;

import java.util.List;
import java.util.Optional;

import vux.codejava.entity.User;
import vux.codejava.response.UserResponse;

public interface UserServices {

	public Optional<User> findByEmail(String email);
	public Optional<User> findByUserId(Long id);
	public List<UserResponse> findUser();
}
