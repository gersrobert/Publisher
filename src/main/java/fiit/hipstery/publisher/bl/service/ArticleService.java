package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleDetailedDTO getArticleById(UUID id) throws IOException;

    List<ArticleSimpleDTO> getArticles();

    List<ArticleSimpleDTO> getArticleListForUser(UUID userId);
}
