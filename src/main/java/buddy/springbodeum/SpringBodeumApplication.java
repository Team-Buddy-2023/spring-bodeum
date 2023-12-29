package buddy.springbodeum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringBodeumApplication {

	public static void main(String[] args) {
		System.out.println("HELLO WORLD");
		SpringApplication.run(SpringBodeumApplication.class, args);
	}

}
