package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.CollectionService;
import fiit.hipstery.publisher.dto.CollectionDTO;
import fiit.hipstery.publisher.dto.CollectionInsertDTO;
import fiit.hipstery.publisher.dto.IdDTO;
import fiit.hipstery.publisher.exception.InternalServerException;
import fiit.hipstery.publisher.exception.PublisherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<CollectionDTO> getCollection(@PathVariable String id, @RequestHeader("Auth-Token") String userId) {
		CollectionDTO collection;
		try {
			collection = collectionService.getCollection(UUID.fromString(id), UUID.fromString(userId));
		} catch (Exception e) {
			logger.error("Error getting collection with id: " + id, e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(collection);
	}

	@GetMapping(path = "/user/{userId}")
	public ResponseEntity<Collection<CollectionDTO>> getCollectionsForUser(@PathVariable String userId) {
		Collection<CollectionDTO> collections;
		try {
			collections = collectionService.getCollectionsForUser(UUID.fromString(userId));
		} catch (Exception e) {
			logger.error("Error getting collection with id: " + userId, e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(collections);
	}

	@PostMapping
	public ResponseEntity<?> insertCollection(@RequestBody CollectionInsertDTO collectionInsertDTO) {
		UUID newCollectionId;
		try {
			newCollectionId = collectionService.insertCollection(collectionInsertDTO);
		} catch (PublisherException e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error("Error assigning collection", e);
			throw new InternalServerException(e);
		}

		IdDTO dto = new IdDTO();
		dto.setId(newCollectionId.toString());
		return ResponseEntity.ok(dto);
	}

	@PutMapping(path = "/assign/{collectionId}/{articleId}")
	public ResponseEntity<?> assignArticleToCollection(@PathVariable String collectionId, @PathVariable String articleId) {
		try {
			collectionService.assignArticle(UUID.fromString(collectionId), UUID.fromString(articleId));
		} catch (PublisherException e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error("Error assigning collection", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping(path = "/update")
	public ResponseEntity<?> assignArticleToCollection(@RequestBody CollectionDTO collectionDTO) {
		CollectionDTO result;
		try {
			result = collectionService.updateCollection(collectionDTO);
		} catch (PublisherException e) {
			logger.info(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error("Error assigning collection", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(result);
	}
}
