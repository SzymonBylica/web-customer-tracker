package com.szymon.springdemo.dao;

import java.util.List;

import com.szymon.springdemo.entity.Customer;

public interface CustomerDAO {
	
	public List<Customer> getCustomers();
	public List<Customer> searchCustomersByName(String theSearchName);
	public Customer getCustomer(int customerId);
	public void saveCustomer(Customer theCustomer);
	public void deleteCustomer(int customerId);

}
