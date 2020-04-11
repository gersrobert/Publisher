package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.initDb.InitDbScript;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Profile("initDb")
@Order(1)
public class FunctionsScript extends InitDbScript {
	@Override
	public void run() {
		entityManager.createNativeQuery("DROP OPERATOR IF EXISTS ~~~~ (text, text);").executeUpdate();
		entityManager.createNativeQuery("DROP FUNCTION IF EXISTS like_rev(text, text)").executeUpdate();

		entityManager.createNativeQuery("CREATE FUNCTION like_rev(text, text) returns boolean as $$ select $2 like $1 $$ LANGUAGE sql;").executeUpdate();
		entityManager.createNativeQuery("CREATE OPERATOR ~~~~ (procedure = like_rev,  leftarg=text, rightarg=text);").executeUpdate();
	}
}
