package com.sms.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sms.persistence.dao.AdminDao;
import com.sms.persistence.domain.Administrator;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder encoder;

	@Override
	public Administrator authenticate(String userName, String password) {
		   if(null != userName && null != password){
			  Administrator admin = adminDao.findByUserName(userName);
			  if(null != admin){
				  String encodedPassword = admin.getPassword();
				  if(encoder.matches(password, encodedPassword)){
					  admin.getAdminRoles();
					  return admin;
				  }
			   }
		    }
		        return null;
	}

}
