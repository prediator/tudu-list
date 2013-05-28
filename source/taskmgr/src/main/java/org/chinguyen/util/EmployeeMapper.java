package org.chinguyen.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.chinguyen.domain.Employee;
import org.chinguyen.response.EmployeeDto;
import org.chinguyen.service.EntityDAO;

public class EmployeeMapper {
	
	@Resource(name="entityService")
	private static EntityDAO<Employee> employeeService = new EntityDAO<Employee>();
	
	public static EmployeeDto map(Employee e) {
		
		EmployeeDto d = new EmployeeDto();
		d.setEmail(e.getEmail());
		d.setEmployeeNumber(e.getEmployeeNumber());
		d.setExtension(e.getExtension());
		d.setFirstName(e.getFirstName());
		d.setJobTitle(e.getJobTitle());
		d.setLastName(e.getLastName());
		d.setOfficeCode(e.getOfficeCode());
		d.setReportsTo(e.getReportsTo());
//		if(e.getReportsTo() != null) {
//			System.out.println("Id is: " + e.getReportsTo());
//			Employee reportsTo = employeeService.get(Employee.class, e.getReportsTo()); 
//			d.setReportsToName(reportsTo.getFirstName() + " " + reportsTo.getLastName());
//		}
		
		return d;
	}
	
	public static List<EmployeeDto> map(List<Employee> employees) {
		List<EmployeeDto> dtos = new ArrayList<EmployeeDto>();
		for (Employee employee: employees) {
			dtos.add(map(employee));
		}
		return dtos;
	}
}
