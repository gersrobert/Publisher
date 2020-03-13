package fiit.hipstery.publisher.db;

import fiit.hipstery.publisher.controller.ArticleController;
import fiit.hipstery.publisher.db.scripts.InitDbScript;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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

	@Autowired
	private List<InitDbScript<?>> initDbScripts;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("initDb();");

		initDbScripts.forEach(InitDbScript::run);
	}

	private List<List<String>> mapStringToTwoDimensionalList(String content) {
		return Arrays.stream(content.split(System.lineSeparator()))
				.filter(item -> item.charAt(0) != '#')
				.map(item -> Arrays.asList(item.split(";")))
				.collect(Collectors.toList());
	}
}
