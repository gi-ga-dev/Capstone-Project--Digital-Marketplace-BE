package com.gigadev.digitalmarketplace.auth.roles;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	@Autowired RoleRepository roleRepo;
	
	public List<Role> searchAllRoles() {
		if(roleRepo.findAll() == null) {
			throw new EntityNotFoundException("No results found...");
		} else return (List<Role>) roleRepo.findAll();
	}
	
	public Role create(Role role) {
		return roleRepo.save(role);
	}

}
