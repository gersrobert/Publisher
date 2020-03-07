package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.ArticleDetailedDTO;

import java.util.List;

public interface ArticleService {

    ArticleDetailedDTO getArticleById(String id);

}
