package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.CollectionService;
import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;
import fiit.hipstery.publisher.entity.Collection;
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
	public CollectionDTO getCollection(UUID id) {
		Collection collection = entityManager.find(Collection.class, id);
		return modelMapper.map(collection, CollectionDTO.class);
	}

	@Override
	@Transactional
	public UUID insertCollection(CollectionInsertDTO collectionInsertDTO) {
		Collection collection = new Collection();
		collection.setDescription(collectionInsertDTO.getDescription());
		collection.setTitle(collectionInsertDTO.getTitle());

		entityManager.persist(collection);
		entityManager.flush();
		return collection.getId();
	}
}
