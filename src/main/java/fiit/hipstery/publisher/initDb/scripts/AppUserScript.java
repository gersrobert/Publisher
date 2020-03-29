package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.InitDbCsvScript;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.PublisherFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(2)
@Profile("initDb")
public class AppUserScript extends InitDbScript<AppUser> {

	public static final int USER_COUNT = 100;
	Role writer;
	Role reader;
	Role publisher_owner;
	Role admin;

	@Override
	public void run() {
		List<Role> roles = entityManager.createQuery("from Role ", Role.class).getResultList();
		writer = roles.stream().filter(role -> role.getName().equals("writer")).findAny().orElseThrow();
		reader = roles.stream().filter(role -> role.getName().equals("reader")).findAny().orElseThrow();
		publisher_owner = roles.stream().filter(role -> role.getName().equals("publisher_owner")).findAny().orElseThrow();
		admin = roles.stream().filter(role -> role.getName().equals("admin")).findAny().orElseThrow();

		for (int i = 0; i < USER_COUNT; i++) {
			AppUser user = new AppUser();
			user.setFirstName(faker.name().firstName());
			user.setLastName(faker.name().lastName());
			user.setUserName(user.getFirstName().toLowerCase() + "." + user.getLastName().toLowerCase());
			user.setRoles(getRolesForIndex(i));

			entityManager.persist(user);
		}
	}

	private List<Role> getRolesForIndex(int i) {
		if (i == 0) {
			return Collections.singletonList(admin);
		}

		List<Role> roles = new ArrayList<>();
		roles.add(reader);

		if (i % (USER_COUNT / 10) == 0) {
			roles.add(writer);
		}
		if (i % (USER_COUNT / 20) == 0) {
			roles.add(publisher_owner);
		}

		return roles;
	}
}
