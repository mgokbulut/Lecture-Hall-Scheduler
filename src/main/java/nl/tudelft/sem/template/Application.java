package nl.tudelft.sem.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Pushing something to the server");
		SpringApplication.run(Application.class, args);
	}

}
