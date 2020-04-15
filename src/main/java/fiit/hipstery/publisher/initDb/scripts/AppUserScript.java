package fiit.hipstery.publisher.initDb.scripts;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Publisher;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Order(4)
@Profile("initDb")
public class AppUserScript extends InitDbScript {

	@Value("${publisher.initDb.appuser.reader_count}")
	private int READER_COUNT;

	@Value("${publisher.initDb.appuser.max_writers_per_publisher}")
	private int MAX_WRITERS_PER_PUBLISHER;

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

	@Autowired
	private EntityCache<Publisher> publisherEntityCache;
	
	@Override
	public void run() {
		writer = roleEntityCache.get("role").stream().filter(role -> role.getName().equals("writer")).findAny().orElseThrow();
		reader = roleEntityCache.get("role").stream().filter(role -> role.getName().equals("reader")).findAny().orElseThrow();
		publisher_owner = roleEntityCache.get("role").stream().filter(role -> role.getName().equals("publisher_owner")).findAny().orElseThrow();
		admin = roleEntityCache.get("role").stream().filter(role -> role.getName().equals("admin")).findAny().orElseThrow();

		createDefaultUsers();
		for (Publisher publisher : publisherEntityCache.get("publisher")) {
			createUser(publisher, reader, writer, publisher_owner);

			for (int i = (int) (Math.random() * MAX_WRITERS_PER_PUBLISHER) - 1; i < MAX_WRITERS_PER_PUBLISHER; i++) {
				createUser(publisher, reader, writer);
			}
		}
		for (int i = 0; i < READER_COUNT; i++) {
			createUser(null, reader);
		}
	}

	private void createUser(Publisher publisher, Role... roles) {
		AppUser user = new AppUser();
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"

		int random = (int) (Math.random() * generators.size());
		user.setUserName(generators.get(random).generate(faker, user));

		if (publisher != null) {
			user.setPublisher(publisher);
		}

		user.setRoles(Arrays.asList(roles));

		entityManager.persist(user);
		appUserCache.append("appUser", user);

		if (user.getRoles().contains(writer)) {
			user.setPublisher(publisherEntityCache.getRandom("publisher"));
			appUserCache.append("author" + user.getPublisher().getName(), user);
		}
	}

	private void createDefaultUsers() {
		AppUser reader = new AppUser();
		reader.setFirstName("Vili");
		reader.setLastName("Citatel");
		reader.setUserName("reader");
		reader.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"
		reader.setRoles(Collections.singletonList(this.reader));
		entityManager.persist(reader);
		appUserCache.append("appUser", reader);

		AppUser writer = new AppUser();
		writer.setFirstName("Domino");
		writer.setLastName("Spisovatel");
		writer.setUserName("writer");
		writer.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"
		writer.setRoles(Arrays.asList(this.reader, this.writer));
		writer.setPublisher(publisherEntityCache.get("publisher").get(0));
		entityManager.persist(writer);
		appUserCache.append("appUser", writer);
		appUserCache.append("author" + writer.getPublisher().getName(), writer);

		AppUser publisher = new AppUser();
		publisher.setFirstName("Janik");
		publisher.setLastName("Publisher");
		publisher.setUserName("publisher");
		publisher.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"
		publisher.setRoles(Arrays.asList(this.reader, this.writer, this.publisher_owner));
		publisher.setPublisher(publisherEntityCache.get("publisher").get(0));
		entityManager.persist(publisher);
		appUserCache.append("appUser", publisher);
		appUserCache.append("author" + publisher.getPublisher().getName(), publisher);

		AppUser admin = new AppUser();
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setUserName("admin");
		admin.setPasswordHash("56b1db8133d9eb398aabd376f07bf8ab5fc584ea0b8bd6a1770200cb613ca005"); // sha256 hashed "heslo"
		admin.setRoles(Collections.singletonList(this.admin));
		entityManager.persist(admin);
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
