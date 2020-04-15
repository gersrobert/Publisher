package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.PublisherService;
import fiit.hipstery.publisher.dto.PublisherDTO;
import fiit.hipstery.publisher.dto.PublisherDetailedDTO;
import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Component
public class PublisherServiceImpl implements PublisherService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PublisherLeadershipDTO> getTopPublishers() {
		List<Object[]> rows = entityManager.createNativeQuery("select * from (" +
				"    SELECT p.name," +
				"           p.id," +
				"           sum(a.like_count) as likes" +
				"    FROM publisher p" +
				"    JOIN article a ON p.id = a.publisher_id" +
				"    GROUP BY p.id) AS p" +
				"    ORDER BY likes DESC ").getResultList();

		return rows.stream().map(this::rowToLeadershipDto).collect(Collectors.toList());
	}

	@Override
	public PublisherDetailedDTO getRowOfPublisher(UUID publisherId) {
		List<Object[]> rows = entityManager.createNativeQuery("select * from (" +
				"    SELECT p.id," +
				"           p.created_at," +
				"           p.name," +
				"           sum(a.like_count) AS likes," +
				"           row_number() OVER (ORDER BY sum(like_count) DESC ) AS rn" +
				"    FROM publisher p" +
				"    JOIN article a ON p.id = a.publisher_id" +
				"    GROUP BY p.id) AS p" +
				"    WHERE id=:publisher_id")
				.setParameter("publisher_id", publisherId.toString())
				.getResultList();

		return rowToDetailedDto(rows.get(0));
	}

	private PublisherLeadershipDTO rowToLeadershipDto(Object[] row) {
		PublisherLeadershipDTO dto = new PublisherLeadershipDTO();
		dto.setName((String) row[0]);
		dto.setId((String) row[1]);
		dto.setLikeCount(((BigInteger) row[2]).intValue());
		return dto;
	}

	private PublisherDetailedDTO rowToDetailedDto(Object[] row) {
		PublisherDetailedDTO dto = new PublisherDetailedDTO();
		dto.setId((String) row[0]);
		dto.setCreatedAt(((Timestamp) row[1]).toLocalDateTime());
		dto.setName((String) row[2]);
		dto.setLikeCount(((Number) row[3]).intValue());
		dto.setOrder(((Number) row[4]).intValue());
		return dto;
	}

}
