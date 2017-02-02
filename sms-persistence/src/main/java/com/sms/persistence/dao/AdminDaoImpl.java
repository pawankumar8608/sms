package com.sms.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sms.persistence.domain.Administrator;

@Repository
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Administrator findByUserName(String userName) {
		   Session session = sessionFactory.openSession();
		   session.beginTransaction();
		   Administrator admin = (Administrator) session.createQuery("from Administrator a where a.userName=:userName").
				                 setParameter("userName", userName).uniqueResult();
		   session.getTransaction().commit();
		   session.close();
		   return admin;
	}

	
}
