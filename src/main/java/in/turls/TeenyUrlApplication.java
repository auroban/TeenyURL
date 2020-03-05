package in.turls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = { JacksonAutoConfiguration.class })
@EnableCaching
@EnableMongoRepositories
@EnableAsync
@EnableScheduling
public class TeenyUrlApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TeenyUrlApplication.class, args);

	}
}
