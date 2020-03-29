package fiit.hipstery.publisher.initDb.config;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import fiit.hipstery.publisher.initDb.dto.SourcesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

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
		String request = API_URL + "everything?apiKey=" + API_KEY + "&language=en&q=";
		return combine(
				restTemplate.getForObject(URI.create(request + "bitcoin"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "AI"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "blockchain"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "corona"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "economy"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "sport"), ArticleListDTO.class),
				restTemplate.getForObject(URI.create(request + "china"), ArticleListDTO.class)
				);
	}

	private ArticleListDTO combine(ArticleListDTO... dtos) {
		ArticleListDTO retVal = new ArticleListDTO();
		retVal.setStatus("ok");

		Arrays.stream(dtos).forEach(dto -> {
			if (dto.getStatus().equals("ok")) {
				retVal.setTotalResults(retVal.getTotalResults() + dto.getTotalResults());
				retVal.getArticles().addAll(dto.getArticles());
			}
		});

		return retVal;
	}

	public SourcesDTO getSources() {
		String request = API_URL + "sources?apiKey=" + API_KEY + "&language=en";
		SourcesDTO response = restTemplate.getForObject(URI.create(request), SourcesDTO.class);

		return response;
	}

}
