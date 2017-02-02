package com.sms.admin.authentication;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sms.admin.service.AdminService;
import com.sms.persistence.domain.AdminRole;
import com.sms.persistence.domain.Administrator;

public class SmsAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder encoder;
	
	public void setEncoder(BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Autowired
	public SmsAuthenticationProvider(AdminService adminService){
		this.adminService = adminService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)throws AuthenticationException {
		   UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		   String username = token.getName();
		   String password = (String) token.getCredentials();
		   Administrator admin = new Administrator();
		   if(null != username){
			   admin = adminService.authenticate(username, password); 
			   if(null != admin){
				   Collection<? extends GrantedAuthority> authorities = getAuthorities(admin);
				   return new UsernamePasswordAuthenticationToken(admin, password, authorities);
			   }
		   }
		      throw new UsernameNotFoundException("Invalid Username or Password");
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Administrator admin) {
		    Collection<AdminRole> roles = admin.getAdminRoles();
		    for(AdminRole role : roles){
		    	String s = role.getRole();
		    	if(s.startsWith("admin")){
		    	   return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		    	}
		    }
     		       return AuthorityUtils.NO_AUTHORITIES;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		   return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
