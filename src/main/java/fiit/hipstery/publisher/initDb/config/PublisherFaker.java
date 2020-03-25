package fiit.hipstery.publisher.initDb.config;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import fiit.hipstery.publisher.initDb.dto.SourcesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Profile("initDb")
public class PublisherFaker {

	@Autowired
	Faker faker;

	@Autowired
	RestTemplate restTemplate;

	public static final String API_URL = "http://newsapi.org/v2/";
	public static final String API_KEY = "ead3711124aa48a0a078a8e6e4b6190e";

	public ArticleListDTO getArticleContent() {
		String request = API_URL + "top-headlines?apiKey=" + API_KEY + "&language=en";
		ArticleListDTO response = restTemplate.getForObject(URI.create(request), ArticleListDTO.class);

		return response;
	}

	public SourcesDTO getSources() {
		String request = API_URL + "sources?apiKey=" + API_KEY + "&language=en";
		SourcesDTO response = restTemplate.getForObject(URI.create(request), SourcesDTO.class);

		return response;
	}

}
