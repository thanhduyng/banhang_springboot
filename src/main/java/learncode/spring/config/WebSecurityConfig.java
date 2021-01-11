package learncode.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import learncode.spring.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auProvider = new DaoAuthenticationProvider();
		auProvider.setPasswordEncoder(passwordEncoder());
		auProvider.setUserDetailsService(userDetailsService());

		return auProvider;
	}

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		String password = "12345678";
		String encrytedPassword = encrytePassword(password);

		System.out.println("Encryted password: " + encrytedPassword);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/").hasAnyAuthority("admin", "nv3");
		
		http.csrf().disable();
		// Các trang không yêu cầu login
		http.authorizeRequests().antMatchers("/come/**","/gio-hang/**",
		"/dang-nhap/**","/dang-nhap/**", "/dang-xuat/**","/da-thanh-toan", "/thanhtoan/**", "/checkout/**",
		"/AddCart/**", "/gio-hang/**", "/DeleteCart/**","/update/**","/kiemtra-dangnhap/**","/dang-ki/**").permitAll();
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/success").permitAll();
		
//			.and()
//			.logout().permitAll()
//			.and()
//			.exceptionHandling().accessDeniedPage("/403")
//			;
		http.logout();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/uploads/**","/static/**", "/admin/**", "/css/**", "/img/**", "/js/**", "/richtext/**", 
				"/tintuc/**","/user/**","/getimage/**");
		super.configure(web);
	}

}
