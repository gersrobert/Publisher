package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;

import java.util.Collection;
import java.util.UUID;

public interface CollectionService {

	/**
	 * Get all active collections
	 */
	Collection<CollectionDTO> getCollectionList();

	/**
	 * Get specific collection by id
	 * @param id collection primary key
	 * @param currentUser currently logged in user
	 * @return dto containing collection data
	 */
	CollectionDTO getCollection(UUID id, UUID currentUser);

	/**
	 * Get collections created by this user
	 * @param userId id of the user
	 * @return list of collections created by this user
	 */
	Collection<CollectionDTO> getCollectionsForUser(UUID userId);

	/**
	 * Create a new collection
	 * @param collectionInsertDTO dto containing data about the collection
	 * @return id of the created collection
	 */
	UUID insertCollection(CollectionInsertDTO collectionInsertDTO);

	/**
	 * Assign article to a collection
	 * @param collectionId id of the collection
	 * @param articleId id of the article
	 */
	void assignArticle(UUID collectionId, UUID articleId);

	/**
	 * Saves changes to a collection
	 * @param collection dto to be persisted
	 * @return updated dto
	 */
	CollectionDTO updateCollection(CollectionDTO collection);

}
