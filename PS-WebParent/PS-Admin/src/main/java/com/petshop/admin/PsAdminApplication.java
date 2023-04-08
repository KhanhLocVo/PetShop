package com.petshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.petshop.common.entity", "com.petshop.admin.user"})
public class PsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsAdminApplication.class, args);
	}

}
