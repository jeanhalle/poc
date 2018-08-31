package com.example.pocQuerydsl;

import com.example.pocQuerydsl.BestPractices.BestPracticesInitializer;
import com.example.pocQuerydsl.BestPractices.BestPracticesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PocQuerydslApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocQuerydslApplication.class, args);
	}

	@Autowired
	BestPracticesRepository repo;

	@PostConstruct
	void initialize() throws Exception {
		new BestPracticesInitializer(repo).init();
	}
}
