package fiit.hipstery.publisher.initDb.config;

import com.github.javafaker.Faker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("initDb")
public class BeanConfigInitDb {

	@Bean
	Faker faker() {
		return new Faker();
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
