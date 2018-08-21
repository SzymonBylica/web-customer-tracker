package com.szymon.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.szymon.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Customer> customerQuery = 
				currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		List<Customer> customers = customerQuery.getResultList();
		
		return customers;
	}

	@Override
	public List<Customer> searchCustomersByName(String theSearchName) {
		System.out.println(theSearchName);
		if (theSearchName == null || theSearchName.trim().length() == 0) {
			return this.getCustomers();
		} else {
			Session currentSession = sessionFactory.getCurrentSession();
			
			Query<Customer> customerQuery = currentSession.createQuery(
				   "from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
				   Customer.class);
			customerQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
			
			List<Customer> customers = customerQuery.getResultList();
		
			return customers;
		}
	}
	
	@Override
	public void saveCustomer(Customer theCustomer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int customerId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Customer theCustomer = currentSession.get(Customer.class, customerId);
		
		return theCustomer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteCustomer(int customerId) {
		Session currentSession = sessionFactory.getCurrentSession();
	    String query = "delete Customer as c where c.id=:customerId";
	    Query customerDeleteQuery = currentSession.
	    		createQuery(query).setParameter("customerId", customerId);
	    
	    customerDeleteQuery.executeUpdate();
	}

}




