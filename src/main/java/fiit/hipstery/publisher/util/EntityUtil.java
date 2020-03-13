package fiit.hipstery.publisher.util;

import fiit.hipstery.publisher.entity.AbstractEntity;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class EntityUtil {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	public <C extends AbstractEntity> String getTableName(Class<C> clazz) {
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(clazz);

		if (hibernateMetadata == null) {
			return null;
		}

		if (hibernateMetadata instanceof AbstractEntityPersister) {
			AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			return persister.getTableName();
		}

		return null;
	}

}
