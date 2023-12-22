package toy.toyproject2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Toyproject2Application {

	public static void main(String[] args) {
		SpringApplication.run(Toyproject2Application.class, args);
	}

}
