package fiit.hipstery.publisher.config;

import fiit.hipstery.publisher.controller.ArticleController;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("initDb")
public class InitDb implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Value("classpath:db/articles.csv")
	private Resource articlesResource;

	@Value("classpath:db/authors.csv")
	private Resource authorsResource;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("initDb();");

		List<List<String>> articleData;
		try {
			articleData = mapStringToTwoDimensionalList(FileUtils.readFileToString(articlesResource.getFile(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error("Error reading file " + articlesResource.getFilename(), e);
			return;
		}
		List<List<String>> authorData;
		try {
			authorData = mapStringToTwoDimensionalList(FileUtils.readFileToString(authorsResource.getFile(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error("Error reading file " + articlesResource.getFilename(), e);
			return;
		}

		List<AppUser> appUsers = new ArrayList<>();
		for (List<String> author : authorData) {
			AppUser user = new AppUser();
			user.setFirstName(author.get(0));
			user.setLastName(author.get(1));
			user.setUserName(author.get(2));

			appUsers.add(user);
		}

		List<Article> articles = new ArrayList<>();
		for (List<String> article : articleData) {
			Article a = new Article();
			List<AppUser> authors = Arrays.stream(article.get(0).split(","))
					.map(Integer::parseInt)
					.map(appUsers::get)
					.collect(Collectors.toList());

			a.setAuthors(authors);
			a.setTitle(article.get(1));
			a.setContent(article.get(2));

			articles.add(a);
		}

		appUsers.forEach(entityManager::persist);
		articles.forEach(entityManager::persist);
	}

	private List<List<String>> mapStringToTwoDimensionalList(String content) {
		return Arrays.stream(content.split(System.lineSeparator()))
				.map(item -> Arrays.asList(item.split(";")))
				.collect(Collectors.toList());
	}
}
