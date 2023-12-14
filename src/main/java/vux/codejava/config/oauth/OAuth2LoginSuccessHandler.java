package vux.codejava.config.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.User;
import vux.codejava.service.UserServices;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	private UserServices userServices;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("AuthenticationSuccessHandler invoked");
		System.out.println("Authentication name: " + authentication.getName());
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		String email = oAuth2User.getEmail();
		System.out.println("login email " + email);
		
		User user = userServices.findByEmail(email).orElse(null);
		
		if(user == null) {
			response.sendRedirect("/sdhsctv/login/authentication/401");
		}
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
