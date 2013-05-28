package org.chinguyen.response;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class EmployeeDto implements Serializable {
	
	private static final long serialVersionUID = 6512682924574976928L;
	
	private Long employeeNumber;
	
	private String lastName;
	
	private String firstName;
	
	private String extension;
	
	private String email;
	
	private String officeCode;
	
	private Long reportsTo;
	
	private String reportsToName;	//firstName + lastName to display on grid 
	
	public String getReportsToName() {
		return reportsToName;
	}

	public void setReportsToName(String reportsToName) {
		this.reportsToName = reportsToName;
	}

	private String jobTitle;
	
	public EmployeeDto() {}
	
	public EmployeeDto(Long employeeNumber, String lastName, String firstName,
			String extension, String email, String officeCode, Long reportsTo,
			String jobTitle) {
		this.employeeNumber = employeeNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.extension = extension;
		this.email = email;
		this.officeCode = officeCode;
		this.reportsTo = reportsTo;
		this.jobTitle = jobTitle;
	}

	public Long getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Long employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public Long getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Long reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
