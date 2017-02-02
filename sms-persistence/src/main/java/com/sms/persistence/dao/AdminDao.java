package com.sms.persistence.dao;

import com.sms.persistence.domain.Administrator;

public interface AdminDao {
	
	public Administrator findByUserName(String userName);

}
