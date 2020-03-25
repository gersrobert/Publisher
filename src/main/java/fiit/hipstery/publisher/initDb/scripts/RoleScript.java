package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class RoleScript extends InitDbScript<Role> {

	@Value("classpath:db/roles.csv")
	private Resource resource;

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
