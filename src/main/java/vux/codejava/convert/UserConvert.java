package vux.codejava.convert;

import org.springframework.stereotype.Component;

import vux.codejava.entity.User;
import vux.codejava.response.UserResponse;

@Component
public class UserConvert {
	public UserResponse response(User entity) {
		UserResponse response = new UserResponse();
		response.setId(entity.getId());
		response.setUsername(entity.getUsername());
		response.setFullName(entity.getFullName());
		return response;
	}
}
