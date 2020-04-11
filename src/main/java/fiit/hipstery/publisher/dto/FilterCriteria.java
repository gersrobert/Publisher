package fiit.hipstery.publisher.dto;

public class FilterCriteria {

	protected String author;
	protected String title;
	protected String category;
	protected String publisher;
	protected int lowerIndex;
	protected int upperIndex;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getLowerIndex() {
		return lowerIndex;
	}

	public void setLowerIndex(int lowerIndex) {
		this.lowerIndex = lowerIndex;
	}

	public int getUpperIndex() {
		return upperIndex;
	}

	public void setUpperIndex(int upperIndex) {
		this.upperIndex = upperIndex;
	}
}
