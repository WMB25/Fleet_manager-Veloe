package com.fleet_manager.veloe;

import org.springframework.boot.SpringApplication;

public class VeloeTestApplication {

	public static void main(String[] args) {
		SpringApplication.from(VeloeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
