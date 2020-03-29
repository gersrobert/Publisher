package fiit.hipstery.publisher.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article extends AbstractEntity {

    protected String title;

    @ManyToOne
    protected Publisher publisher;

    @ManyToMany
    protected List<Category> categories;

    @OneToMany(mappedBy = "article")
    protected List<Comment> comments;

    @Type(type="text")
    protected String content;

    @Formula(value = "(SELECT count(*) FROM app_user_article_relation aar WHERE aar.article_id = id AND aar.relation_type = 'LIKE')")
    protected int likeCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
