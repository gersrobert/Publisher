package fiit.hipstery.publisher.initDb.config;

import fiit.hipstery.publisher.entity.AbstractEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("initDb")
@Scope("singleton")
public class EntityCache<E extends AbstractEntity> {

	protected Map<Class<? extends AbstractEntity>, List<E>> entities = new HashMap<>();

	public void save(E entity) {
		entities.putIfAbsent(entity.getClass(), new ArrayList<>());
		entities.get(entity.getClass()).add(entity);
	}

	public List<E> getEntities(Class<E> c) {
		return entities.get(c);
	}
}
