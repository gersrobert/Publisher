package fiit.hipstery.publisher.dto;

import java.util.Objects;

public abstract class AbstractDTO {

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractDTO)) return false;
		AbstractDTO that = (AbstractDTO) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
