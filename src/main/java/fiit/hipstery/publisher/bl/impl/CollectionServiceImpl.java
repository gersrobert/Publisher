package fiit.hipstery.publisher.bl.impl;

import com.github.javafaker.App;
import fiit.hipstery.publisher.bl.service.CollectionService;
import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.entity.Collection;
import fiit.hipstery.publisher.exception.PublisherException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CollectionDTO> getCollectionList() {
		List<Collection> collections = entityManager.createQuery("from Collection ", Collection.class).getResultList();
		return collections.stream().map(c -> modelMapper.map(c, CollectionDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CollectionDTO getCollection(UUID id, UUID currentUser) {
		Collection collection = entityManager.find(Collection.class, id);
		List<Article> articles = collection.getArticles().stream().peek(article -> { // TODO toto by chcelo nejak normalne spravit
			boolean liked = entityManager.createQuery("from AppUserArticleRelation where appUser.id=:userId and article.id=:articleId and relationType='LIKE'")
					.setParameter("userId", currentUser)
					.setParameter("articleId", article.getId())
					.getResultList().size() != 0;
			article.setLiked(liked);

			List<AppUser> authors = entityManager.createQuery("select appUser from AppUserArticleRelation rel where rel.article=:a and rel.relationType='AUTHOR'", AppUser.class)
					.setParameter("a", article)
					.getResultList();
			article.setAuthors(authors);

		}).collect(Collectors.toList());
		collection.setArticles(articles);
		return modelMapper.map(collection, CollectionDTO.class);
	}

	@Override
	public java.util.Collection<CollectionDTO> getCollectionsForUser(UUID userId) {
		List<Collection> collections = entityManager.createQuery(
				"from Collection where author.id=:userId", Collection.class)
				.setParameter("userId", userId)
				.getResultList();

		return collections.stream().map(c -> modelMapper.map(c, CollectionDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UUID insertCollection(CollectionInsertDTO collectionInsertDTO) {
		Collection collection = new Collection();
		collection.setDescription(collectionInsertDTO.getDescription());
		collection.setTitle(collectionInsertDTO.getTitle());

		List<Article> articles = entityManager.createQuery("from Article where id in :ids", Article.class)
				.setParameter("ids", collectionInsertDTO.getArticles().stream().map(UUID::fromString).collect(Collectors.toList()))
				.getResultList();

		collection.setArticles(articles);
		collection.setAuthor(entityManager.find(AppUser.class, UUID.fromString(collectionInsertDTO.getAuthor())));
		entityManager.persist(collection);
		entityManager.flush();
		return collection.getId();
	}

	@Override
	@Transactional
	public void assignArticle(UUID collectionId, UUID articleId) {
		Collection collection = entityManager.find(Collection.class, collectionId);
		Article article = entityManager.find(Article.class, articleId);

		List<Article> collectionArticles = collection.getArticles();
		if (collectionArticles.contains(article)) {
			throw new PublisherException("duplicate_article_in_collection");
		}
		collectionArticles.add(article);
		collection.setArticles(collectionArticles);

		entityManager.merge(collection);
	}
}
