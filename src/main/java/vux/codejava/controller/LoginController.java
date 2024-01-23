package vux.codejava.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vux.codejava.entity.CustomUserChangePassword;
import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.User;
import vux.codejava.lib.EncrytedPassword;
import vux.codejava.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository repo;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		
		return "register";
	}
	
	@GetMapping("/access_login")
	public String loginAccount(RedirectAttributes rAttributes, Principal principal) {
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		String role = getRole(userDetails.getAuthorities());
		System.out.println("ROLE------------" + role);
		if(role.equals("ROLE_USER")) {
			return "redirect:/index";
		}else if(role.equals("ROLE_ADMIN") || role.equals("ROLE_ROOT")){
			return "redirect:/admin";
		}else if(role.equals("ROLE_EDITOR")) {
			return "redirect:/editor";
		}
		return "redirect:/index";
	}
	
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encoderPassword = EncrytedPassword.encrytedPassword(user.getPassword());
		user.setPassword(encoderPassword);
		
		repo.save(user);
		
		return "login";
	}
	
	@GetMapping("/profile")
	public String viewProfilePage(Model model,
			Principal principal) {
		model.addAttribute("pageTitle", "Thông tin cá nhân");
		model.addAttribute("pagePath", "profile");
		return "profile";
	}
	
	@PostMapping("/process_changepassword")
	public String changePassword(Model model,
			CustomUserChangePassword userPassword,
			Principal principal) {
		String currentPassword = userPassword.getCurrentPassword();
		String newPassword = userPassword.getNewPassword();
		String confirmPassword = userPassword.getConfirmPassword();
		
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		
		List<String> msgError = new ArrayList<String>();
		boolean check = true;
		
		if(!EncrytedPassword.checkPassword(currentPassword, userDetails.getPassword())) {
			msgError.add("Mật khẩu cũ không đúng");
			check = false;
		}else if(newPassword.length() < 6){
			msgError.add("Mật khẩu mới cần lớn hơn 6 ký tự");
			check = false;
		}else if(currentPassword.equalsIgnoreCase(newPassword)) {		
			msgError.add("Mật khẩu mới trùng mật khẩu cũ");
			check = false;
		}else if(!newPassword.equalsIgnoreCase(confirmPassword)) {
			msgError.add("Nhập lại mật khẩu không đúng");
			check = false;
		}
		
		if(check) {
			repo.updateUser(EncrytedPassword.encrytedPassword(newPassword), userDetails.getUsername());
			msgError.add("Đổi mật khẩu thành công");
			return loginPage();
		}
		
		model.addAttribute("msg", msgError);
		
		return viewProfilePage(model, principal);
	}
	
	/*
	@GetMapping("/403")
	public String errorPage() {
		return "403";
	}
	*/
	@GetMapping("/login/authentication/401")
	public String errorPage() {
		return "error/401";
	}
	
	
	/* get role */
	private String getRole(Collection<? extends GrantedAuthority> collection) {
		String role = "";
		if(collection != null && !collection.isEmpty()) {
			for(GrantedAuthority authority : collection) {
				role = authority.getAuthority();
			}
		}
		return role;
	}
}
