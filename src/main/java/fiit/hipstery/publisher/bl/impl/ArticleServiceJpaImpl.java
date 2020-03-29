package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleInsertDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("jpa")
public class ArticleServiceJpaImpl extends ArticleServiceNativeImpl implements ArticleService {

	@PersistenceContext
	private EntityManager entityManager;

//	@Override
//	public ArticleDetailedDTO getArticleById(UUID id) {
//		Article article = entityManager.getReference(Article.class, id);
//
//		ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
//		articleDetailedDTO.setId(article.getId().toString());
//		articleDetailedDTO.setAuthors(article.getAuthors().stream().map(this::appUserToDTO).collect(Collectors.toList()));
//		articleDetailedDTO.setTitle(article.getTitle());
//		articleDetailedDTO.setContent(article.getContent());
//		articleDetailedDTO.setPublishedAt(article.getCreatedAt());
//		return articleDetailedDTO;
//	}
//
//	@Override
//	public List<ArticleSimpleDTO> getArticles() {
//		List<Article> articles = entityManager.createQuery("from Article", Article.class).getResultList();
//		return articles.stream().map(this::articleToArticleSimpleDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ArticleSimpleDTO> getArticleListForUser(UUID userId) {
//		return null;
//	}

	private ArticleSimpleDTO articleToArticleSimpleDTO(Article article) {
		ArticleSimpleDTO articleSimpleDTO = new ArticleSimpleDTO();
		articleSimpleDTO.setId(article.getId().toString());
//		articleSimpleDTO.setAuthors(article.getAuthors().stream().map(this::appUserToDTO).collect(Collectors.toList()));
		articleSimpleDTO.setPublishedAt(article.getCreatedAt());
		articleSimpleDTO.setTitle(article.getTitle());

		return articleSimpleDTO;
	}

	private AppUserDTO appUserToDTO(AppUser appUser) {
		AppUserDTO dto = new AppUserDTO();
		dto.setFirstName(appUser.getFirstName());
		dto.setLastName(appUser.getLastName());
		dto.setUserName(appUser.getUserName());
		dto.setId(appUser.getId().toString());
		return dto;
	}
}
