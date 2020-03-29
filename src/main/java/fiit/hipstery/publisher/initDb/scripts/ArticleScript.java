package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(3)
public class ArticleScript extends InitDbScript<Article> {

	@Value("classpath:db/articles.csv")
	private Resource resource;

	private List<AppUser> appUsers;

	@Override
	public void run() {
		 appUsers = entityManager.createQuery("from AppUser", AppUser.class).getResultList();
		 super.run();
	}

	@Override
	protected Resource getDataFile() {
		return resource;
	}

	@Override
	protected Article mapRowToEntity(List<String> row) {
		Article article = new Article();
		List<AppUser> authors = Arrays.stream(row.get(0).split(","))
				.map(Integer::parseInt)
				.map(appUsers::get)
				.collect(Collectors.toList());

//		article.setAuthors(authors);
		article.setTitle(row.get(1));
		article.setContent(row.get(2));
		return article;
	}
}
