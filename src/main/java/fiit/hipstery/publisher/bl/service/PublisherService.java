package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;

import java.util.List;

public interface PublisherService {

	List<PublisherLeadershipDTO> getTopPublishers();

}
