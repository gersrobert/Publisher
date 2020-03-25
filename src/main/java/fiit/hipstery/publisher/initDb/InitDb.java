package fiit.hipstery.publisher.initDb;

import fiit.hipstery.publisher.controller.ArticleController;
import fiit.hipstery.publisher.initDb.requests.InitDbRequest;
import fiit.hipstery.publisher.initDb.scripts.InitDbScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Profile("initDb")
public class InitDb implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private List<InitDbScript<?>> initDbScripts;

	@Autowired
	private List<InitDbRequest> initDbRequests;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("initDb();");
		initDbScripts.forEach(InitDbScript::run);
		initDbRequests.forEach(InitDbRequest::run);
		logger.info("initDb(); completed");
	}
}
