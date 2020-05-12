package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.CollectionService;
import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.AppUserArticleRelation.RelationType;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.entity.Collection;
import fiit.hipstery.publisher.exception.PublisherException;
import fiit.hipstery.publisher.repository.ArticleRepository;
import fiit.hipstery.publisher.repository.CollectionRepository;
import fiit.hipstery.publisher.repository.RelationRepository;
import fiit.hipstery.publisher.repository.UserRepository;
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
	private ArticleRepository articleRepository;

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private RelationRepository relationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CollectionDTO> getCollectionList() {
		List<Collection> collections = collectionRepository.findAll();
		return collections.stream().map(c -> modelMapper.map(c, CollectionDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CollectionDTO getCollection(UUID id, UUID currentUser) {
		Collection collection = collectionRepository.getOne(id);
		List<Article> articles = collection.getArticles().stream().peek(article -> {
			boolean liked = relationRepository.existsByAppUser_IdAndArticle_IdAndRelationType(
							currentUser,
							article.getId(),
							RelationType.LIKE.toString()
					);
			article.setLiked(liked);

			List<AppUser> authors = articleRepository.getAuthor(article.getId());
			article.setAuthors(authors);

		}).collect(Collectors.toList());
		collection.setArticles(articles);
		return modelMapper.map(collection, CollectionDTO.class);
	}

	@Override
	public java.util.Collection<CollectionDTO> getCollectionsForUser(UUID userId) {
		List<Collection> collections = collectionRepository.getAllByAuthor_Id(userId);
		return collections.stream().map(c -> modelMapper.map(c, CollectionDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UUID insertCollection(CollectionInsertDTO collectionInsertDTO) {
		Collection collection = new Collection();
		collection.setDescription(collectionInsertDTO.getDescription());
		collection.setTitle(collectionInsertDTO.getTitle());

		List<Article> articles = articleRepository.getAllByIdIn(
				collectionInsertDTO.getArticles().stream().map(UUID::fromString).collect(Collectors.toList())
		);

		collection.setArticles(articles);
		collection.setAuthor(userRepository.getOne(UUID.fromString(collectionInsertDTO.getAuthor())));
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

	@Override
	@Transactional
	public CollectionDTO updateCollection(CollectionDTO collection) {
		Collection entity = entityManager.find(Collection.class, UUID.fromString(collection.getId()));
		entity.setArticles(articleRepository.getAllByIdIn(
				collection.getArticles().stream().map(
						article -> UUID.fromString(article.getId())
				).collect(Collectors.toList()))
		);
		entity.setTitle(collection.getTitle());
		entity.setDescription(collection.getDescription());

		entityManager.merge(entity);
		entityManager.flush();
		return modelMapper.map(entity, CollectionDTO.class);
	}
}
