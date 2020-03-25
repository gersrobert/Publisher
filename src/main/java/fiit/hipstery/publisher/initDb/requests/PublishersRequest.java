package fiit.hipstery.publisher.initDb.requests;

import fiit.hipstery.publisher.entity.Publisher;
import fiit.hipstery.publisher.initDb.dto.SourcesDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Profile("initDb")
public class PublishersRequest extends InitDbRequest {

	@Override
	@Transactional
	public void run() {
		SourcesDTO sources = publisherFaker.getSources();

		sources.getSources().forEach(source -> {
			Publisher publisher = new Publisher();
			publisher.setName(source.getName());

			entityManager.persist(publisher);
		});
	}
}
