package org.chinguyen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.chinguyen.domain.Customer;
import org.chinguyen.domain.Employee;
import org.chinguyen.response.EmployeeDto;
import org.chinguyen.response.JqgridResponse;
import org.chinguyen.service.EmployeeService;
import org.chinguyen.service.EntityDAO;
import org.chinguyen.util.EmployeeMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
//	@Resource(name="employeeService")
//	private EmployeeService employeeService;
	
	@Resource(name="entityService")
	private EntityDAO<Employee> employeeService;
	
	@RequestMapping
	public String getHomePage() {
		return "employees";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<EmployeeDto> records(
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord) {
		//default is page 1 and 10 records
		page = page == null ? 1 : page;
		rows = rows == null ? 10 : rows;
		System.out.println("get employees, page: " + page + ", rows: " + rows + " sidx: " + sidx + " " + sord);
		
		List<Employee> employees = employeeService.retrieveEntitiesByPage(page, rows, sidx, sord, Employee.class);

		List<EmployeeDto> employeeDtos = EmployeeMapper.map(employees);
		
		JqgridResponse<EmployeeDto> response = new JqgridResponse<EmployeeDto>();
		response.setRows(employeeDtos);
//		response.setRecords(employeeService.countEmployee().toString());
//		response.setTotal(Long.valueOf((employeeService.countEmployee()/rows) + 1).toString());
		response.setRecords(employeeService.count(Employee.class).toString());
		response.setTotal(Long.valueOf((employeeService.count(Employee.class)/rows) + 1).toString());
		response.setPage(page.toString());
		
		return response;
	}
	
	
}
