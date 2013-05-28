package org.chinguyen.domain;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 6512682924574976928L;
	
	@Id
	@Column(name = "customerNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerNumber;
	
	@Column(name = "customerName")
	private String customerName;
	
	@Column(name = "contactLastName")
	private String contactLastName;
	
	@Column(name = "contactFirstName")
	private String contactFirstName;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "addressLine1")
	private String addressLine1;
	
	@Column(name = "addressLine2")
	private String addressLine2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "postalCode")
	private String postalCode;
	
	@Column(name = "country")
	private String country;
	
	//@Column(name = "salesRepEmployeeNumber")
	//private Long salesRepEmployeeNumber;
	
	@ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber", nullable = false)
	private Employee employee;
	
	@Column(name = "creditLimit")
	private Double creditLimit;
	
//	@OneToOne(mappedBy="user", cascade={CascadeType.ALL})
//	private Role role;

	public Customer() {}
	
	public Customer(String customerName, String contactLastName, String contactFirstName, String phone,
			String addressLine1, String city, String country) {
		this.customerName = customerName;
		this.contactFirstName = contactFirstName;
		this.contactLastName = contactLastName;
		this.phone = phone;
		this.addressLine1 = addressLine1;
		this.city = city;
		this.country = country;
	}

	public Customer(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

//	public Long getSalesRepEmployeeNumber() {
//		return salesRepEmployeeNumber;
//	}
//
//	public void setSalesRepEmployeeNumber(Long salesRepEmployeeNumber) {
//		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
//	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
