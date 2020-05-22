package fiit.hipstery.publisher.initDb;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.initDb.config.PublisherFaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("initDb")
public abstract class InitDbScript implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected Faker faker;

	@Autowired
	protected PublisherFaker publisherFaker;

	/**
	 * Override this method to implement script logic
	 */
	@Override
	public abstract void run();
}
