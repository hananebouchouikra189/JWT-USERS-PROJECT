package org.sid.secservice.sec.service;

import java.util.List;

import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;
import org.sid.secservice.sec.repo.AppRoleRepository;
import org.sid.secservice.sec.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	AppRoleRepository appRoleRepository;
	AppUserRepository appUserRepository;
	private PasswordEncoder passwordEncoder;
	
	public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder=passwordEncoder;
		this.appRoleRepository = appRoleRepository;
		this.appUserRepository = appUserRepository;
	}

	@Override
	public AppUser addNewUser(AppUser appUser) {
		String pw=appUser.getPassword();
		appUser.setPassword(passwordEncoder.encode(pw));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
		// TODO Auto-generated method stub
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser appUser=appUserRepository.findByUsername(username);
		AppRole appRole=appRoleRepository.findByRoleName(roleName);
		appUser.getAppRoles().add(appRole);
		
	}

	@Override
	public AppUser loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> listUsers() {
		// TODO Auto-generated method stub
		return appUserRepository.findAll();
	}

}
