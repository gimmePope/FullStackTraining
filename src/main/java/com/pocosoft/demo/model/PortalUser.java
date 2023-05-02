package com.pocosoft.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PORTAL_USERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PortalUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true, name="EMAIL")
	private String email;
	@Column(unique = true, name = "USERNAME")
	private String username;
	@Column(name = "USER_PASSWORD")
	private String userPassword;
	@Column(name = "USER_ROLE")
	private String userRole;

}
