package com.sms.admin.service;

import com.sms.persistence.domain.Administrator;

public interface AdminService {
	
	public Administrator authenticate(String userName, String password);

}
