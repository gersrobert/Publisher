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
		List<Object[]> rows = entityManager.createNativeQuery("SELECT " +
				"       p.name         AS p_name," +
				"       p.id           AS p_id," +
				"       count(auar.id) AS like_count" +
				"   FROM article a" +
				"       LEFT OUTER JOIN publisher p on a.publisher_id = p.id" +
				"       JOIN app_user_article_relation auar on a.id = auar.article_id and auar.relation_type = 'LIKE'" +
				"   GROUP BY p.name, p.id" +
				"   HAVING count(auar.id) > 10000" +
				"   ORDER BY like_count DESC").getResultList();

		return rows.stream().map(this::rowToLeadershipDto).collect(Collectors.toList());
	}

	@Override
	public PublisherDetailedDTO getRowOfPublisher(UUID publisherId) {
		System.out.println(publisherId);
		List<Object[]> rows = entityManager.createNativeQuery("WITH a AS ( " +
		"   	SELECT " +
		"          p.id as p_id, " +
		"          p.created_at as p_created_at, " +
		"          p.name AS p_name, " +
		"          count(auar.id) AS like_count," +
		"          row_number() over (ORDER BY count(auar.id) DESC) AS rn " +
		"      FROM article a " +
		"          LEFT OUTER JOIN publisher p on a.publisher_id = p.id " +
		"          JOIN app_user_article_relation auar on a.id = auar.article_id and auar.relation_type = 'LIKE' " +
		"      GROUP BY p.name, p.id " +
		"      ORDER BY like_count DESC" +
		"   ) SELECT " +
		"       p_id, " +
		"       p_created_at, " +
		"       p_name, " +
		"       like_count, " +
		"       rn " +
		"   FROM a " +
		"   WHERE p_id=:publisher_id").setParameter("publisher_id", publisherId.toString()).getResultList();

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
