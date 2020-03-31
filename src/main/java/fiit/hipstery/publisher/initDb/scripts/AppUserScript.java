package fiit.hipstery.publisher.initDb.scripts;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.InitDbScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(2)
@Profile("initDb")
public class AppUserScript extends InitDbScript {

	public static final int USER_COUNT = 100_000;
	Role writer;
	Role reader;
	Role publisher_owner;
	Role admin;

	@Autowired
	List<UserNameGenerator> generators;

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

			int random = (int) (Math.random() * generators.size());
			user.setUserName(generators.get(random).generate(faker, user));

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

		if (i % (USER_COUNT / 20) == 0) {
			roles.add(writer);
		}
		if (i % (USER_COUNT / 50) == 0) {
			roles.add(publisher_owner);
		}

		return roles;
	}

	public interface UserNameGenerator {
		String generate(Faker faker, AppUser user);
	}

	@Component
	@Profile("initDb")
	public static class Gen1 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getFirstName().toLowerCase() + "." + user.getLastName().toLowerCase() + "." + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen2 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getFirstName().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen3 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getLastName().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen4 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.rickAndMorty().character().replace(' ', '_') + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen5 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.harryPotter().character().replace(' ', '_') + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen6 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.esports().player().replace(' ', '_') + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen7 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.hipster().word().replace(' ', '_') + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen8 implements UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.superhero().name().replace(' ', '_') + "_" + UUID.randomUUID().toString().substring(0, 6);
		}
	}
}
