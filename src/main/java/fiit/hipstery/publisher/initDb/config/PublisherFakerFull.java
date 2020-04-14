package fiit.hipstery.publisher.initDb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiit.hipstery.publisher.initDb.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
@Profile("initDb & initDbFull")
public class PublisherFakerFull extends PublisherFaker {

	@Autowired
	List<RandomArticleProvider> providers;

	@Autowired
	ObjectMapper objectMapper;

	private final String articleListPath = System.getProperty("user.dir") + "/src/main/resources/db/articles.json";

	private String request;

	@Override
	public int getMaxIndex() {
		return 1;
	}

	@Override
	public List<ArticleDTO> getArticleContent() {
		if (index++ == 0) {
			try {
				ArticleDTO[] articles = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/main/resources/db/articles-copy.json"), ArticleDTO[].class);
				return Arrays.asList(articles);
			} catch (IOException e) {
				throw new RuntimeException();
			}
		} else {
			List<ArticleDTO> articleDTOList = new ArrayList<>();

			String content = faker.lorem().paragraph(30);
			content += System.lineSeparator() + System.lineSeparator();
			content += faker.lorem().paragraph(80);
			content += System.lineSeparator() + System.lineSeparator();
			content += faker.lorem().paragraph(50);

			for (int i = 0; i < 5_000; i++) {
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setTitle(String.format("%s %s %s %s",
						faker.rickAndMorty().character(),
						faker.hacker().verb(),
						faker.hacker().adjective(),
						faker.hacker().noun()
				));
				articleDTO.setCategories(Collections.singleton("technology"));
				articleDTO.setPublishedAt(LocalDate.now().toString());
				articleDTO.setLikeCount((int) (Math.random() * 100));
				articleDTO.setContent(content);
				articleDTOList.add(articleDTO);
			}

			return articleDTOList;
		}
	}

	private List<ArticleDTO> populateFromApi() {
		request = API_URL + "everything?apiKey=" + API_KEY + "&language=en&q=";

		List<ArticleDTO> retVal = new ArrayList<>();
		retVal.addAll(getArticlesForKeyword(request, "AI", "technology", "AI"));
		retVal.addAll(getArticlesForKeyword(request, "blockchain", "technology", "blockchain"));
		retVal.addAll(getArticlesForKeyword(request, "covid-19", "healthcare", "covid-19"));
		retVal.addAll(getArticlesForKeyword(request, "economy", "finance"));
		retVal.addAll(getArticlesForKeyword(request, "bitcoin", "technology", "blockchain", "bitcoin"));
		retVal.addAll(getArticlesForKeyword(request, "startup", "finance", "technology"));
		retVal.addAll(getArticlesForKeyword(request, "fintech", "finance", "technology"));
		retVal.addAll(getArticlesForKeyword(request, "user-experience", "technology", "ux"));
		retVal.addAll(getArticlesForKeyword(request, "art", "art"));
		retVal.addAll(getArticlesForKeyword(request, "cooking", "cooking"));
		retVal.addAll(getArticlesForKeyword(request, "sport", "sport"));
		retVal.addAll(getArticlesForKeyword(request, "EU-news", "news", "europe"));
		retVal.addAll(getArticlesForKeyword(request, "China-news", "news", "asia"));
		retVal.addAll(getArticlesForKeyword(request, "USA-news", "news", "usa"));
		retVal.addAll(getArticlesForKeyword(request, "politics", "politics"));
		retVal.addAll(getArticlesForKeyword(request, "software-engineering", "technology", "software-engineering"));
		retVal.addAll(getArticlesForKeyword(request, "business", "business"));
		retVal.addAll(getArticlesForKeyword(request, "space", "science", "space"));
		retVal.addAll(getArticlesForKeyword(request, "science", "science"));
		retVal.addAll(getArticlesForKeyword(request, "NASA", "science", "space", "nasa"));
		retVal.addAll(getArticlesForKeyword(request, "budget", "politics", "finance"));
		retVal.addAll(getArticlesForKeyword(request, "agriculture", "news", "agriculture"));
		retVal.addAll(getArticlesForKeyword(request, "farming", "news", "agriculture", "farming"));
		retVal.addAll(getArticlesForKeyword(request, "industry", "industry"));
		retVal.addAll(getArticlesForKeyword(request, "manufacturing", "industry", "manufacturing"));
		retVal.addAll(getArticlesForKeyword(request, "transport", "industry", "transport"));
		retVal.addAll(getArticlesForKeyword(request, "global-warming", "climate-change", "global-warming"));
		retVal.addAll(getArticlesForKeyword(request, "climate-change", "climate-change"));

		providers.forEach(provider -> {
			while (provider.hasNext()) {
				retVal.addAll(provider.getNext());
			}
		});

		Collections.shuffle(retVal);

		try {
			objectMapper.writeValue(new File(articleListPath), retVal);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public abstract class RandomArticleProvider {

		private Set<String> keywords = new HashSet<>();
		private boolean hasNext = true;
		private int duplicates = 0;

		public boolean hasNext() {
			return hasNext;
		}

		public List<ArticleDTO> getNext() {
			String keyword = getKeyword();
			if (keywords.contains(keyword)) {
				if (duplicates++ > 3) {
					hasNext = false;
				}
				hasNext = false;
				return Collections.emptyList();
			}
			keywords.add(keyword);

			return getArticlesForKeyword(request, keyword.replace(" ", "%20"), getCategories());
		}

		protected abstract String getKeyword();

		protected abstract String[] getCategories();
	}

	@Component
	@Profile("initDb")
	public class Provider1 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.hacker().abbreviation();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"technology", "networking"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider2 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.hacker().adjective() + "%20" + faker.hacker().noun();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"technology", "networking"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider3 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.hacker().adjective() + "%20" + faker.hacker().verb();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"technology", "networking"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider4 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.hacker().noun() + "%20" + faker.hacker().verb();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"technology", "networking"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider5 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.rickAndMorty().character();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider6 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.rickAndMorty().location();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider7 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.harryPotter().character();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider8 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.harryPotter().location();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider9 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.ancient().god();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"history", "mythology"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider10 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.ancient().hero();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"history", "mythology"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider11 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.ancient().titan();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"history", "mythology"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider12 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.artist().name();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"art"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider13 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.superhero().name();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider14 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().event();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider15 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().game();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider16 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().league();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider17 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().player();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider18 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().event();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider19 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.esports().team();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"gaming", "esports"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider20 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.gameOfThrones().character();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider21 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.gameOfThrones().city();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider22 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.gameOfThrones().dragon();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider23 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.gameOfThrones().house();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider24 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.lordOfTheRings().character();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}

	@Component
	@Profile("initDb")
	public class Provider25 extends RandomArticleProvider {

		@Override
		protected String getKeyword() {
			return faker.lordOfTheRings().location();
		}

		@Override
		protected String[] getCategories() {
			return new String[]{"entertainment", "television"};
		}
	}
}
