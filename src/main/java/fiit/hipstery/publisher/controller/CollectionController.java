package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.CollectionService;
import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;
import fiit.hipstery.publisher.dto.IdDTO;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@Controller
@RequestMapping(path = "/collection")
public class CollectionController extends AbstractController {

	@Autowired
	private CollectionService collectionService;

	Logger logger = LoggerFactory.getLogger(CollectionController.class);

	@GetMapping
	public ResponseEntity<Collection<CollectionDTO>> getCollectionList() {
		Collection<CollectionDTO> collections;
		try {
			collections = collectionService.getCollectionList();
		} catch (Exception e) {
			logger.error("Error getting collections", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(collections);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CollectionDTO> getCollection(@PathVariable String id) {
		CollectionDTO collection;
		try {
			collection = collectionService.getCollection(UUID.fromString(id));
		} catch (Exception e) {
			logger.error("Error getting collection with id: " + id, e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(collection);
	}

	@PostMapping(path = "insert")
	public ResponseEntity<IdDTO> insertCollection(@RequestBody CollectionInsertDTO collectionInsertDTO) {
		throw new RuntimeException();
	}
}
