package io.termproject.collegefootballdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.termproject.collegefootballdashboard")
public class CollegeFootballDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeFootballDashboardApplication.class, args);
	}

}

