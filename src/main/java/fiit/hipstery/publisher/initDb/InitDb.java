package fiit.hipstery.publisher.initDb;

import fiit.hipstery.publisher.initDb.scripts.IndexScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Utility class for inserting default data into database.
 * Executes all instances of {@link InitDbScript} bean.
 */
@Component
@Profile("initDb")
public class InitDb implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(InitDb.class);

	@Autowired
	private List<InitDbScript> initDbScripts;

	@Autowired
	private ConfigurableApplicationContext context;

	@Autowired
	private AddArticles addArticles;

	@Autowired
	private IndexScript indexScript;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

		logger.info("initDb();");
		initDbScripts.forEach(InitDbScript::run);

		entityManager.flush();
		transactionManager.commit(transaction);

		addArticles.insert();

		transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
		indexScript.run();
		entityManager.flush();
		transactionManager.commit(transaction);
	}
}
