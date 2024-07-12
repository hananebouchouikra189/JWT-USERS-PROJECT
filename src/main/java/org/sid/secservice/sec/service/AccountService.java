package org.sid.secservice.sec.service;

import java.util.List;

import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;

public interface AccountService {

	AppUser addNewUser(AppUser appUser);
	AppRole addNewRole(AppRole appRole);
	void addRoleToUser(String username,String roleName);
	AppUser loadUserByUsername(String username);
	List<AppUser> listUsers();
}
