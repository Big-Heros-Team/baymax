package com.example.bayMax;

import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import com.example.bayMax.Infrastructure.RolesRepository;
import com.example.bayMax.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class BayMaxApplication implements CommandLineRunner {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolesRepository rolesRepository;

	public static void main(String[] args) {

		SpringApplication.run(BayMaxApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findAll().isEmpty()){
		Date date = new Date(100,10,1);
		Roles admin=new Roles("ADMIN");
		Roles doctor=new Roles("DOCTOR");
		Roles user=new Roles("USER");

		rolesRepository.saveAll(List.of(admin,doctor,user));
		Users newUser = new Users("firstname","lastname",date,"Amman, Jordan","A++", 999999L,"admin",bCryptPasswordEncoder.encode("password"));
		newUser.addRole(rolesRepository.findRolesByName("ADMIN"));
		 userRepository.save(newUser);}

	}
}
