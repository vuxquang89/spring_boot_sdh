package vux.codejava.service;

import java.util.Optional;

import vux.codejava.entity.User;

public interface UserServices {

	public Optional<User> findByEmail(String email);
}
