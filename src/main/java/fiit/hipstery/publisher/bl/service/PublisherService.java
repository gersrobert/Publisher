package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.PublisherDetailedDTO;
import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;

import java.util.List;
import java.util.UUID;

public interface PublisherService {

	/**
	 * Fetch ranking list of top publishers
	 */
	List<PublisherLeadershipDTO> getTopPublishers();

	/**
	 * Get possition of publisher in ranking list
	 * @param publisherId unique Id of desired publisher
	 */
	PublisherDetailedDTO getRowOfPublisher(UUID publisherId);
}
