package org.sid.secservice;

import java.util.ArrayList;

import org.sid.secservice.sec.entities.AppRole;
import org.sid.secservice.sec.entities.AppUser;
import org.sid.secservice.sec.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true)//pour permet les annotations des authorites
public class SicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SicServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            // Adding roles
            accountService.addNewRole(new AppRole(null,"USER"));
            accountService.addNewRole(new AppRole(null,"ADMIN"));
            accountService.addNewRole(new AppRole(null,"CUSTOMER_MANAGER"));
            accountService.addNewRole(new AppRole(null,"PRODUCT_MANAGER"));
            accountService.addNewRole(new AppRole(null,"BILLS_MANAGER"));
            
            // Adding users
            accountService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user3","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user4","1234",new ArrayList<>()));
            
            // Assigning roles to users
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("user3", "PRODUCT_MANAGER");
            accountService.addRoleToUser("user4", "BILLS_MANAGER");
            accountService.addRoleToUser("user4", "USER");
            accountService.addRoleToUser("user3", "USER");
        };
    }
}
