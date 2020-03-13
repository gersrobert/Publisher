package fiit.hipstery.publisher.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Article extends AbstractEntity {

    @ManyToMany
    protected List<AppUser> authors;

    protected String title;

    @ManyToOne
    protected Publisher publisher;

    @ManyToMany
    protected List<Category> categories;

    @Type(type="text")
    protected String content;

    @Formula(value = "(SELECT count(*) FROM app_user_liked_articles WHERE app_user_liked_articles.liked_articles_id = id)")
    protected int likeCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AppUser> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AppUser> author) {
        this.authors = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
