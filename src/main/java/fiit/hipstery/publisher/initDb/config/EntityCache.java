package fiit.hipstery.publisher.initDb.config;

import fiit.hipstery.publisher.entity.AbstractEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("initDb")
@Scope("singleton")
public class EntityCache<E> {

	protected Map<String, List<E>> entities = new HashMap<>();

	public void append(String key, E object) {
		entities.putIfAbsent(key, new ArrayList<>());
		entities.get(key).add(object);
	}

	public List<E> get(String key) {
		return entities.get(key);
	}

	public E getRandom(String key) {
		return entities.get(key).get((int) (entities.get(key).size() * Math.random()));
	}
}
