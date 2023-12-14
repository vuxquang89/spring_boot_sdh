package vux.codejava.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import vux.codejava.lib.Convert;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		*/
		List<String> roleNames = Convert.getRoleNames(user.getRole());
		
		//List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if(roleNames != null) {
			for(String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				authorities.add(authority);
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		//return user.isEnabled();
		return true;
	}

	public User getUser() {
		return user;
	}
	
	public String getDistrict() {
		return user.getDistrict();
	}
	
	public String getFullName() {
		return user.getFullName();
	}
}
