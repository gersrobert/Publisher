package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;

public class PublisherDetailedDTO extends PublisherLeadershipDTO {
	protected LocalDateTime createdAt;
	protected int order;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
