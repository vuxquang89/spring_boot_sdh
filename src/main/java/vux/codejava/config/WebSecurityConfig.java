package vux.codejava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import vux.codejava.config.oauth.CustomOAuth2User;
import vux.codejava.config.oauth.CustomOAuth2UserService;
import vux.codejava.config.oauth.OAuth2LoginSuccessHandler;
import vux.codejava.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	private AppConfig appConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//cai dat dich vu tim kiem User trong database
		//va cai dat password
		appConfig = new AppConfig();
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(appConfig.passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable(); //hạn chế sử dụng HTTP GET vì nó có thể làm leak thông tin
		//http.authorizeRequests()
	    // other security information ...
		//.and().csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository());
		
		http.csrf(c -> {
			c.ignoringAntMatchers("/csrfdiabled/**");
		});
		
		//cac trang khong yeu cau dang nhap
		http.authorizeHttpRequests().antMatchers("/", "/login", "/logout", "/register", "/oauth/**").permitAll();
		
		//trang userInfo yeu cau pahi login voi vai tro ROLE_USER hoac ROLE_ADMIN
		//neu chua login, se chuyen redirect toi trang login
		http.authorizeHttpRequests().antMatchers("/user/**", 
				"/operate/**", 
				"/profile",
				"/shift").hasAnyAuthority("ROLE_ADMIN","ROLE_USER", "ROLE_ROOT");
		
		//trang chi danh cho ADMIN
		http.authorizeHttpRequests().antMatchers("/admin", "/admin/**").hasAnyAuthority("ROLE_ROOT", "ROLE_ADMIN");
		
		//khi nguoi dung login voi vai tro xx
		//nhung co gang dang nhap trang cua yy
		//thi nem ra ngoai le
		http.authorizeHttpRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		//cau hinh remember me, thoi gian la 3 * 24 * 60 * 60 giay
		http.rememberMe().key("sdh@sctv").tokenValiditySeconds(259200);
		
		//cau hinh login
		http.authorizeHttpRequests()
				.and()
				.formLogin()
					//submit login
					.loginProcessingUrl("/j_spring_security_check")//submit xu ly login
					.loginPage("/login")
					.defaultSuccessUrl("/access_login")//dang nhap thanh cong
					.failureUrl("/login?error")//loi dang nhap
					.usernameParameter("username")//lay gia tri username tu form
					.passwordParameter("password")//lay gia tri password tu form
				.and()
					.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
						.userService(oauthUserService)
					.and()
					.successHandler(oAuth2LoginSuccessHandler)
					//.defaultSuccessUrl("/list")
				
					//cau hinh logout
					.and()
					.logout()
						.permitAll()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login");
					
	}
	
	@Autowired
	private CustomOAuth2UserService oauthUserService;
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
}


