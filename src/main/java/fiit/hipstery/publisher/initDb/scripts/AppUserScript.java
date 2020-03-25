package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Publisher;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.config.PublisherFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(2)
public class AppUserScript extends InitDbScript<AppUser> {

	@Autowired
	private PublisherFaker faker;

	@Value("classpath:db/app_users.csv")
	private Resource resource;

	private List<Role> roles;

	@Override
	public void run() {
		faker.getSources();

		roles = entityManager.createQuery("from Role ", Role.class).getResultList();
		super.run();
	}

	@Override
	protected Resource getDataFile() {
		return resource;
	}

	@Override
	protected AppUser mapRowToEntity(List<String> row) {
		AppUser user = new AppUser();
		List<Role> roles = Arrays.stream(row.get(0).split(","))
				.map(Integer::parseInt)
				.map(this.roles::get)
				.collect(Collectors.toList());

		user.setFirstName(row.get(1));
		user.setLastName(row.get(2));
		user.setUserName(row.get(3));
		user.setRoles(roles);
		return user;
	}
}
