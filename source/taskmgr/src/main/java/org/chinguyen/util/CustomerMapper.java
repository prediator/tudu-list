package org.chinguyen.util;

import java.util.ArrayList;
import java.util.List;

import org.chinguyen.domain.Customer;
import org.chinguyen.response.CustomerDto;

public class CustomerMapper {

	public static CustomerDto map(Customer customer) {
		CustomerDto dto = new CustomerDto();
		dto.setAddressLine1(customer.getAddressLine1());
		dto.setAddressLine2(customer.getAddressLine2());
		dto.setCity(customer.getCity());
		dto.setContactFirstName(customer.getContactFirstName());
		dto.setContactLastName(customer.getContactLastName());
		dto.setCountry(customer.getCountry());
		dto.setCreditLimit(customer.getCreditLimit());
		dto.setCustomerName(customer.getCustomerName());
		dto.setCustomerNumber(customer.getCustomerNumber());
		dto.setPhone(customer.getPhone());
		dto.setPostalCode(customer.getPostalCode());
		//dto.setSalesRepEmployeeNumber(customer.getSalesRepEmployeeNumber());
		dto.setSalesRepEmployee(customer.getEmployee().getFirstName()
				+ " " + customer.getEmployee().getLastName());
		dto.setSalesRepEmployeeNumber(customer.getEmployee().getEmployeeNumber());
		dto.setState(customer.getState());
		return dto;
	}
	
	public static List<CustomerDto> map(List<Customer> customers) {
		List<CustomerDto> dtos = new ArrayList<CustomerDto>();
		for (Customer customer: customers) {
			dtos.add(map(customer));
		}
		return dtos;
	}
}
