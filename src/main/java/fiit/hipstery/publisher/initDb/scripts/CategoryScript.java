package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.Category;
import fiit.hipstery.publisher.initDb.InitDbCsvScript;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("initDb")
@Order(3)
public class CategoryScript extends InitDbCsvScript<Category> {

	@Value("classpath:db/categories.csv")
	private Resource resource;

	@Override
	protected Resource getDataFile() {
		return resource;
	}

	@Override
	protected Category mapRowToEntity(List<String> row) {
		Category category = new Category();
		category.setName(row.get(0));
		return category;
	}
}
