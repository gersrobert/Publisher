package fiit.hipstery.publisher.initDb.config;

import fiit.hipstery.publisher.initDb.dto.ArticleDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Profile("initDb & !full")
public class PublisherFakerShort extends PublisherFaker {

	@Override
	public List<ArticleDTO> getArticleContent() {
		String request = API_URL + "everything?apiKey=" + API_KEY + "&language=en&q=";
		List<ArticleDTO> retVal = new ArrayList<>();

		retVal.addAll(getArticlesForKeyword(request,"AI", "technology", "AI"));
		retVal.addAll(getArticlesForKeyword(request,"covid-19", "healthcare", "covid-19"));
		retVal.addAll(getArticlesForKeyword(request,"economy", "finance"));
		retVal.addAll(getArticlesForKeyword(request,"bitcoin", "technology", "blockchain", "bitcoin"));
		retVal.addAll(getArticlesForKeyword(request,"news", "news"));

		Collections.shuffle(retVal);
		return retVal;
	}
}
