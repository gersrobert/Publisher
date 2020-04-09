package fiit.hipstery.publisher.initDb.scripts;

import com.github.javafaker.App;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.entity.Comment;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("initDb")
@Order(6)
public class CommentScript extends InitDbScript {

	@Autowired
	private EntityCache<Article> articleEntityCache;

	@Autowired
	private EntityCache<AppUser> appUserEntityCache;

	@Override
	public void run() {
		List<AppUser> appUsers = appUserEntityCache.getEntities(AppUser.class);

		articleEntityCache.getEntities(Article.class).forEach(article -> {
			int commentCount = (int) (Math.random() * 5);
			for (int i = 0; i < commentCount; i++) {
				Comment comment = new Comment();
				comment.setArticle(article);
				comment.setAuthor(appUsers.get((int) (Math.random() * appUsers.size())));
				comment.setContent(faker.rickAndMorty().quote());
				entityManager.persist(comment);
			}
		});
	}
}
