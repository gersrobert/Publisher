package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.PublisherService;
import fiit.hipstery.publisher.dto.PublisherLeadershipDTO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
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

		return rows.stream().map(this::rowToDto).collect(Collectors.toList());
	}

	private PublisherLeadershipDTO rowToDto(Object[] row) {
		PublisherLeadershipDTO dto = new PublisherLeadershipDTO();
		dto.setName((String) row[0]);
		dto.setId((String) row[1]);
		dto.setLikeCount(((BigInteger) row[2]).intValue());
		return dto;
	}
}
