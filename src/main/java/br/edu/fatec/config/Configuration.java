package br.edu.fatec.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EntityScan(basePackages="br.edu.fatec")
@ComponentScan(basePackages= "br.edu.fatec")
@EnableJpaRepositories("br.edu.fatec.repository")
@EnableAutoConfiguration
public class Configuration extends SpringBootServletInitializer{

	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Configuration.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Configuration.class, args);
	}
	
}
