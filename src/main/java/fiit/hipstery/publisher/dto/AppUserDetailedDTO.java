package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;
import java.util.List;

public class AppUserDetailedDTO extends AppUserDTO {
	protected LocalDateTime createdAt;
	protected List<String> roles;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@JsonSetter
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getRoles() {
		return roles;
	}

	@JsonSetter
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
