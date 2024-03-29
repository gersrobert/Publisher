package fiit.hipstery.publisher.initDb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class ArticleListDTO {

	String status;
	int totalResults = 0;
	List<ArticleDTO> articles = new ArrayList<>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}

	public static class ArticleDTO {

		String author;
		String title;
		String description;
		String url;
		String urlToImage;
		String publishedAt;
		String content;

		@JsonGetter
		public String getAuthor() {
			return author;
		}

		@JsonSetter
		public void setAuthor(String author) {
			this.author = author;
		}

		@JsonGetter
		public String getTitle() {
			return title;
		}

		@JsonSetter
		public void setTitle(String title) {
			this.title = title;
		}

		@JsonGetter
		public String getDescription() {
			return description;
		}

		@JsonSetter
		public void setDescription(String description) {
			this.description = description;
		}

		@JsonGetter
		public String getUrl() {
			return url;
		}

		@JsonSetter
		public void setUrl(String url) {
			this.url = url;
		}

		@JsonGetter
		public String getUrlToImage() {
			return urlToImage;
		}

		@JsonSetter
		public void setUrlToImage(String urlToImage) {
			this.urlToImage = urlToImage;
		}

		@JsonGetter
		public String getPublishedAt() {
			return publishedAt;
		}

		@JsonSetter
		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		@JsonGetter
		public String getContent() {
			return content;
		}

		@JsonSetter
		public void setContent(String content) {
			this.content = content;
		}

		public static class Source {
			String id;
			String name;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
	}
}
