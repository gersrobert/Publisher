package fiit.hipstery.publisher.initDb.config;

import fiit.hipstery.publisher.initDb.dto.ArticleDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Profile("initDb & full")
public class PublisherFakerFull extends PublisherFaker {

	@Override
	public List<ArticleDTO> getArticleContent() {
		String request = API_URL + "everything?apiKey=" + API_KEY + "&language=en&q=";
		List<ArticleDTO> retVal = new ArrayList<>();

		retVal.addAll(getArticlesForKeyword(request,"AI", "technology", "AI"));
		retVal.addAll(getArticlesForKeyword(request,"blockchain", "technology", "blockchain"));
		retVal.addAll(getArticlesForKeyword(request,"covid-19", "healthcare", "covid-19"));
		retVal.addAll(getArticlesForKeyword(request,"economy", "finance"));
		retVal.addAll(getArticlesForKeyword(request,"bitcoin", "technology", "blockchain", "bitcoin"));
		retVal.addAll(getArticlesForKeyword(request,"startup", "finance", "technology"));
		retVal.addAll(getArticlesForKeyword(request,"fintech", "finance", "technology"));
		retVal.addAll(getArticlesForKeyword(request,"user-experience", "technology", "ux"));
		retVal.addAll(getArticlesForKeyword(request,"art", "art"));
		retVal.addAll(getArticlesForKeyword(request,"cooking", "cooking"));
		retVal.addAll(getArticlesForKeyword(request,"sport", "sport"));
		retVal.addAll(getArticlesForKeyword(request,"EU-news", "news", "europe"));
		retVal.addAll(getArticlesForKeyword(request,"China-news", "news", "asia"));
		retVal.addAll(getArticlesForKeyword(request,"USA-news", "news", "usa"));
		retVal.addAll(getArticlesForKeyword(request,"politics", "politics"));
		retVal.addAll(getArticlesForKeyword(request,"software-engineering", "technology", "software-engineering"));
		retVal.addAll(getArticlesForKeyword(request,"business", "business"));
		retVal.addAll(getArticlesForKeyword(request,"space", "science", "space"));
		retVal.addAll(getArticlesForKeyword(request,"science", "science"));
		retVal.addAll(getArticlesForKeyword(request,"NASA", "science", "space", "nasa"));
		retVal.addAll(getArticlesForKeyword(request,"budget", "politics", "finance"));
		retVal.addAll(getArticlesForKeyword(request,"agriculture", "news", "agriculture"));
		retVal.addAll(getArticlesForKeyword(request,"farming", "news", "agriculture", "farming"));
		retVal.addAll(getArticlesForKeyword(request,"industry", "industry"));
		retVal.addAll(getArticlesForKeyword(request,"manufacturing", "industry", "manufacturing"));
		retVal.addAll(getArticlesForKeyword(request,"transport", "industry", "transport"));
		retVal.addAll(getArticlesForKeyword(request,"global-warming", "climate-change", "global-warming"));
		retVal.addAll(getArticlesForKeyword(request,"climate-change", "climate-change"));

		Collections.shuffle(retVal);
		return retVal;
	}
}
