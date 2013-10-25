package org.chinguyen.controller;

import java.util.List;

import javax.annotation.Resource;

import org.chinguyen.domain.Role;
import org.chinguyen.domain.User;
import org.chinguyen.response.JqgridResponse;
import org.chinguyen.response.StatusResponse;
import org.chinguyen.response.UserDto;
import org.chinguyen.service.UserService;
import org.chinguyen.util.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping
	public String getHomePage() {
		return "users";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<UserDto> records(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord) {
		//default is page 1 and 10 records
		page = page == null ? 1 : page;
		rows = rows == null ? 10 : rows;
		System.out.println("get users, page: " + page + ", rows: " + rows + " sidx: " + sidx + " " + sord);
		
		List<User> users = userService.retrieveUsersByPage(page, rows, sidx, sord);

		List<UserDto> userDtos = UserMapper.map(users);
		
		JqgridResponse<UserDto> response = new JqgridResponse<UserDto>();
		response.setRows(userDtos);
		response.setRecords(userService.countUsers().toString());
		response.setTotal(Long.valueOf((userService.countUsers()/rows) + 1).toString());
		response.setPage(page.toString());
		
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody UserDto get(@RequestBody UserDto user) {
		return UserMapper.map(userService.get(user.getId()));
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse create(
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName) {
		
		User newUser = new User(username, password, firstName, lastName);
		newUser.setPassword(password);
		Boolean result = userService.add(newUser);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam String username,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role) {
		
		User existingUser = new User(username, firstName, lastName, new Role(role));
		Boolean result = userService.update(existingUser);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam String username) {

		User existingUser = new User(username);
		Boolean result = userService.delete(existingUser);
		return new StatusResponse(result);
	}
	public void changePassword(){
		
	}
}
