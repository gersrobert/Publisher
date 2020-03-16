package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Profile("sqlOnly")
public class ArticleServiceSqlOnlyImpl extends ArticleServiceImpl {

	@Override
	public ArticleDetailedDTO getArticleById(UUID id) {
		// TODO
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<ArticleSimpleDTO> getArticles() {
		// TODO
		throw new RuntimeException("Not yet implemented");
	}

}
