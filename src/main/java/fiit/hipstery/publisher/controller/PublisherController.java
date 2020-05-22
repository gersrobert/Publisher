package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.PublisherService;
import fiit.hipstery.publisher.dto.PublisherDetailedDTO;
import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/publisher")
public class PublisherController extends AbstractController {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private PublisherService publisherService;

	@GetMapping("/top")
	public ResponseEntity<List<PublisherLeadershipDTO>> getTopPublishers() {
		List<PublisherLeadershipDTO> response;
		try {
			response = publisherService.getTopPublishers();
		} catch (Exception e) {
			logger.error("Error getting top publishers", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/rowOf/{publisherId}")
	public ResponseEntity<PublisherDetailedDTO> getRowOfPublisher(@PathVariable UUID publisherId) {
		PublisherDetailedDTO response;
		try {
			response = publisherService.getRowOfPublisher(publisherId);
		} catch (Exception e) {
			logger.error("Error getting publisher", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.ok(response);

	}
}
