package fiit.hipstery.publisher.initDb.scripts;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(3)
@Profile("initDb")
public class AppUserScript extends InitDbScript {

	public static final int USER_COUNT = 100_000;
	
	private Role writer;
	private Role reader;
	private Role publisher_owner;
	private Role admin;

	@Autowired
	private List<UserNameGenerator> generators;

	@Autowired
	private EntityCache<AppUser> appUserCache;

	@Autowired
	private EntityCache<Role> roleEntityCache;
	
	@Override
	public void run() {
		writer = roleEntityCache.getEntities(Role.class).stream().filter(role -> role.getName().equals("writer")).findAny().orElseThrow();
		reader = roleEntityCache.getEntities(Role.class).stream().filter(role -> role.getName().equals("reader")).findAny().orElseThrow();
		publisher_owner = roleEntityCache.getEntities(Role.class).stream().filter(role -> role.getName().equals("publisher_owner")).findAny().orElseThrow();
		admin = roleEntityCache.getEntities(Role.class).stream().filter(role -> role.getName().equals("admin")).findAny().orElseThrow();

		for (int i = 0; i < USER_COUNT; i++) {
			AppUser user = new AppUser();
			user.setFirstName(faker.name().firstName());
			user.setLastName(faker.name().lastName());
			user.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"

			int random = (int) (Math.random() * generators.size());
			user.setUserName(generators.get(random).generate(faker, user));

			user.setRoles(getRolesForIndex(i));

			entityManager.persist(user);
			appUserCache.save(user);
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

	public abstract static class UserNameGenerator {
		protected int i = 0;
		public abstract String generate(Faker faker, AppUser user);
	}

	@Component
	@Profile("initDb")
	public static class Gen1 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getFirstName().toLowerCase() + "." + user.getLastName().toLowerCase() + "." + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen2 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getFirstName().toLowerCase() + "_" + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen3 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return user.getLastName() + "_" + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen4 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.rickAndMorty().character().replace(' ', '_') + "_" + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen5 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.harryPotter().character().replace(' ', '_') + "_" + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen7 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.hipster().word().replace(' ', '_') + "-" + i++;
		}
	}

	@Component
	@Profile("initDb")
	public static class Gen8 extends UserNameGenerator {
		@Override
		public String generate(Faker faker, AppUser user) {
			return faker.superhero().name().replace(' ', '_') + "-" + i++;
		}
	}
}
