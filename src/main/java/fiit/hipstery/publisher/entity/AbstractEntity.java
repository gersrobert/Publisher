package fiit.hipstery.publisher.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity {

	public static final String STATE_ACTIVE = "ACTIVE";
	public static final String STATE_DELETED = "DELETED";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	@Type(type = "uuid-char")
	protected UUID id;

	@CreationTimestamp
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	protected LocalDateTime updatedAt;

	protected String state = STATE_ACTIVE;

	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
