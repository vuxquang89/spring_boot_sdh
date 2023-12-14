package vux.codejava.util;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import vux.codejava.config.oauth.CustomOAuth2User;
import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.User;
import vux.codejava.service.UserServices;

@Component
public class PrincipalObject {
	
	@Autowired
	private UserServices userService;

	public CustomUserDetails getCustomUserDetails(Principal principal) {
		if(((Authentication)principal).getPrincipal().getClass().getSimpleName().equals("CustomUserDetails")) {
			System.out.println("Login with local");
			return (CustomUserDetails)((Authentication)principal).getPrincipal();
			
		}else {
			CustomOAuth2User userOath = (CustomOAuth2User)((Authentication)principal).getPrincipal();
			User user = userService.findByEmail(userOath.getEmail()).get();
			return new CustomUserDetails(user);
		}
		
	}
}
