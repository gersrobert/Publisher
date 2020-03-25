package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.AbstractEntity;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InitDbScript<E extends AbstractEntity> implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void run() {
		List<List<String>> data = getData(getDataFile());
		data.stream().map(this::mapRowToEntity).forEach(entityManager::persist);
	}

	protected abstract Resource getDataFile();

	protected abstract E mapRowToEntity(List<String> row);

	private List<List<String>> getData(Resource resource) {
		try {
			return mapStringToTwoDimensionalList(FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error("Error reading file " + resource.getFilename(), e);
			throw new RuntimeException(e);
		}
	}

	private List<List<String>> mapStringToTwoDimensionalList(String content) {
		return Arrays.stream(content.split(System.lineSeparator()))
				.filter(item -> item.charAt(0) != '#')
				.map(item -> Arrays.asList(item.split(";")))
				.collect(Collectors.toList());
	}
}
