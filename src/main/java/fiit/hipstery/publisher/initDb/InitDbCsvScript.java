package fiit.hipstery.publisher.initDb;

import fiit.hipstery.publisher.entity.AbstractEntity;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Profile("initDb")
public abstract class InitDbCsvScript<E extends AbstractEntity> extends InitDbScript {

	@Autowired
	private EntityCache<E> entityCache;

	private Class<E> typeOfE;

	protected InitDbCsvScript(Class<E> type) {
		this.typeOfE = type;
	}

	@Override
	public void run() {
		List<List<String>> data = getData(getDataFile());
		data.stream().map(this::mapRowToEntity).forEach(e -> {
			entityManager.persist(e);
			entityCache.append(typeOfE.getSimpleName().toLowerCase(), e);
		});
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
