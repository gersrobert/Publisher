package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.InitDbCsvScript;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@Profile("initDb")
public class RoleScript extends InitDbCsvScript<Role> {

	@Value("classpath:db/roles.csv")
	private Resource resource;

	protected RoleScript() {
		super(Role.class);
	}

	@Override
	protected Resource getDataFile() {
		return resource;
	}

	@Override
	protected Role mapRowToEntity(List<String> row) {
		Role role = new Role();
		role.setName(row.get(0));
		return role;
	}
}
