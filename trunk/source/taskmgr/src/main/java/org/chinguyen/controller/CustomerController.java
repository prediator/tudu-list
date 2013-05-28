package org.chinguyen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.chinguyen.domain.Customer;
import org.chinguyen.response.CustomerDto;
import org.chinguyen.response.JqgridResponse;
import org.chinguyen.service.CustomerService;
import org.chinguyen.service.EntityDAO;
import org.chinguyen.util.CustomerMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	//@Resource(name="customerService")
	//private CustomerService customerService;
	
	@Resource(name="entityService")
	private EntityDAO<Customer> customerService;
	
	@RequestMapping
	public String getHomePage() {
		return "customers";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<CustomerDto> records(
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord) {
		//default is page 1 and 10 records
		page = page == null ? 1 : page;
		rows = rows == null ? 10 : rows;
		System.out.println("get customers, page: " + page + ", rows: " + rows + " sidx: " + sidx + " " + sord);
		
		//List<Customer> customers = customerService.retrieveUsersByPage(page, rows, sidx, sord);
		List<Customer> customers = customerService.retrieveEntitiesByPage(page, rows, sidx, sord, Customer.class);

		List<CustomerDto> customerDtos = CustomerMapper.map(customers);
		
		JqgridResponse<CustomerDto> response = new JqgridResponse<CustomerDto>();
		response.setRows(customerDtos);
//		response.setRecords(customerService.countCustomer().toString());
//		response.setTotal(Long.valueOf((customerService.countCustomer()/rows) + 1).toString());
		response.setRecords(customerService.count(Customer.class).toString());
		response.setTotal(Long.valueOf((customerService.count(Customer.class)/rows) + 1).toString());
		response.setPage(page.toString());
		
		return response;
	}
	
	
}
