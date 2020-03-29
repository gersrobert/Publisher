package fiit.hipstery.publisher.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    protected AppUser author;

    @ManyToOne
    protected Article article;

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
