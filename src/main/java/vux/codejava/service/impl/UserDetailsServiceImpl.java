package vux.codejava.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.User;
import vux.codejava.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	public UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + " was not found in the databse!");
		}
		/*
		List<String> roleNames = Convert.getRoleNames(user.getRole());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if(roleNames != null) {
			for(String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		*/
		//UserDetails userDeatils = (UserDetails) new User(user.getUsername(), user.getPassword(), grantList);
		
		return new CustomUserDetails(user);
	}

}
