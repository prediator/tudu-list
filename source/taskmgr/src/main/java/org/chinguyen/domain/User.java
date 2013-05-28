package org.chinguyen.domain;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.DigestUtils;

@Entity
@Table(name = "user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6512682924574976928L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(unique=true, name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
//	@OneToOne(mappedBy="user", cascade={CascadeType.ALL})
//	private Role role;

	public User() {}
	
	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String username, String firstName, String lastName, Role role) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		//this.role = role;
	}

	public User(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.md5DigestAsHex(password.getBytes());
	}

//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
}
