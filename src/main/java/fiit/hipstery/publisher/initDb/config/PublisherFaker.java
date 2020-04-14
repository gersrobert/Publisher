package fiit.hipstery.publisher.initDb.config;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.initDb.dto.ArticleDTO;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import fiit.hipstery.publisher.initDb.dto.SourcesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PublisherFaker {

	@Autowired
	Faker faker;

	@Autowired
	RestTemplate restTemplate;

	protected Logger logger = LoggerFactory.getLogger(getClass().getName());

	public static final String API_URL = "http://newsapi.org/v2/";
	public static final String API_KEY = "5960adeca78845af8b9fa2a77d5afcf0";

	protected int index = 0;

	public boolean hasNext() {
		return index < getMaxIndex();
	}

	public abstract List<ArticleDTO> getArticleContent();
	public abstract int getMaxIndex();

	protected List<ArticleDTO> getArticlesForKeyword(String request, String keyword, String... assignedCategories) {
		ArticleListDTO response;
		try {
			response = restTemplate.getForObject(URI.create(request + keyword), ArticleListDTO.class);
		} catch (Exception e) {
			return Collections.emptyList();
		}
		if (response != null && response.getStatus().equals("ok")) {
			return response.getArticles().stream().map(a -> {
				ArticleDTO result = new ArticleDTO();
				result.setAuthor(a.getAuthor());
				result.setContent(formatContent(a.getContent()));
				result.setDescription(a.getDescription());
				result.setPublishedAt(a.getPublishedAt());
				result.setTitle(a.getTitle());
				result.setUrl(a.getUrl());
				result.setUrlToImage(a.getUrlToImage());
				result.setCategories(Arrays.asList(assignedCategories));
				return result;
			}).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	protected String formatContent(String content) {
		int lookupState = 0;
		String result = "";

		if (content != null && !content.isEmpty()) {
			int i;
			for (i = content.length() - 1; i > 0; i--) {
				if (content.charAt(i) == ']') {
					lookupState = 1;
					continue;
				}
				if (lookupState == 1 && content.charAt(i) == '[') {
					lookupState = 2;
					break;
				}
			}

			result = content.substring(0, i);
		}
		result += faker.lorem().paragraph(30);
		result += System.lineSeparator() + System.lineSeparator();
		result += faker.lorem().paragraph(80);
		result += System.lineSeparator() + System.lineSeparator();
		result += faker.lorem().paragraph(50);
		return result;
	}

	public SourcesDTO getSources() {
		String request = API_URL + "sources?apiKey=" + API_KEY + "&language=en";
		SourcesDTO response = restTemplate.getForObject(URI.create(request), SourcesDTO.class);

		return response;
	}

}
