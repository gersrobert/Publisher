package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;

import java.util.Collection;
import java.util.UUID;

public interface CollectionService {

	public Collection<CollectionDTO> getCollectionList();
	public CollectionDTO getCollection(UUID id, UUID currentUser);
	public Collection<CollectionDTO> getCollectionsForUser(UUID userId);
	public UUID insertCollection(CollectionInsertDTO collectionInsertDTO);
	public void assignArticle(UUID collectionId, UUID articleId);

}
