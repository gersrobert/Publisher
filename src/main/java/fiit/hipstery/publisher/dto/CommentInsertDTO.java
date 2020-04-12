package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.UUID;

public class CommentInsertDTO {
	private String content;
	private UUID articleId;
	private UUID appUserId;

	public String getContent() {
		return content;
	}

	@JsonSetter
	public void setContent(String content) {
		this.content = content;
	}

	public UUID getArticleId() {
		return articleId;
	}

	@JsonSetter
	public void setArticleId(UUID articleId) {
		this.articleId = articleId;
	}

	public UUID getAppUserId() {
		return appUserId;
	}

	@JsonSetter
	public void setAppUserId(UUID appUserId) {
		this.appUserId = appUserId;
	}
}
