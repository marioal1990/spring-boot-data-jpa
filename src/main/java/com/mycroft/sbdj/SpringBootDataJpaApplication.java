package com.mycroft.sbdj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {
	
//	@Autowired
//	private FileServices fileServices;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		this.fileServices.deleteUploadsDirectory();
//		this.fileServices.createUploadsDirectory();
	}
}
