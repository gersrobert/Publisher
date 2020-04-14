package fiit.hipstery.publisher.initDb.scripts;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Profile("initDb")
@Order(10)
public class IndexScript implements Runnable {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void run() {
		entityManager.createNativeQuery("CREATE INDEX ON app_user_article_relation (article_id, app_user_id, relation_type)").executeUpdate();
		entityManager.createNativeQuery("CREATE INDEX ON article (id, like_count, title)").executeUpdate();
		entityManager.createNativeQuery("CREATE INDEX ON category (name)").executeUpdate();
	}
}
