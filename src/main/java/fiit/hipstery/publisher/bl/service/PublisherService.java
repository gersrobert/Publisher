package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.PublisherDetailedDTO;
import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;

import java.util.List;
import java.util.UUID;

public interface PublisherService {

	List<PublisherLeadershipDTO> getTopPublishers();

	PublisherDetailedDTO getRowOfPublisher(UUID publisherId);
}
