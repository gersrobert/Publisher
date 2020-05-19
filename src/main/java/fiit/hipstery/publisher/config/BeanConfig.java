package fiit.hipstery.publisher.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!initDb")
public class BeanConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
