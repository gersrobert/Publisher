package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;

import java.util.Collection;
import java.util.UUID;

public interface CollectionService {

	Collection<CollectionDTO> getCollectionList();
	CollectionDTO getCollection(UUID id, UUID currentUser);
	Collection<CollectionDTO> getCollectionsForUser(UUID userId);
	UUID insertCollection(CollectionInsertDTO collectionInsertDTO);
	void assignArticle(UUID collectionId, UUID articleId);
	CollectionDTO updateCollection(CollectionDTO collection);

}
