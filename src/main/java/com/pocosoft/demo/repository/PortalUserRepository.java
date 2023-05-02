package com.pocosoft.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocosoft.demo.model.PortalUser;

public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
	
	public PortalUser findByEmail(String email);

}
