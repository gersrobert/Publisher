package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.Publisher;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import fiit.hipstery.publisher.initDb.dto.SourcesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("initDb")
@Order(3)
public class PublisherScript extends InitDbScript {

	@Autowired
	private EntityCache<Publisher> publisherEntityCache;

	@Override
	public void run() {
		SourcesDTO sources = publisherFaker.getSources();

		sources.getSources().forEach(source -> {
			Publisher publisher = new Publisher();
			publisher.setName(source.getName());

			entityManager.persist(publisher);
			publisherEntityCache.append("publisher", publisher);
		});
	}
}
