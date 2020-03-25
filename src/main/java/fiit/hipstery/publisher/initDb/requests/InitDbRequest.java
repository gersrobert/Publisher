package fiit.hipstery.publisher.initDb.requests;

import fiit.hipstery.publisher.initDb.config.PublisherFaker;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class InitDbRequest implements Runnable {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected PublisherFaker publisherFaker;

}
