package fiit.hipstery.publisher.dto;

public class CommentDTO extends AbstractDTO {

	protected String content;
	protected AppUserDTO author;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AppUserDTO getAuthor() {
		return author;
	}

	public void setAuthor(AppUserDTO author) {
		this.author = author;
	}
}
