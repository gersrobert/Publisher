package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.initDb.InitDbScript;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("initDb")
@Order(10)
public class IndexScript extends InitDbScript {
	@Override
	public void run() {
		entityManager.createNativeQuery("CREATE INDEX ON app_user_article_relation (article_id, app_user_id, relation_type)").executeUpdate();
		entityManager.createNativeQuery("CREATE INDEX ON article (id, like_count, title)").executeUpdate();
		entityManager.createNativeQuery("CREATE INDEX ON category (name)").executeUpdate();
	}
}
