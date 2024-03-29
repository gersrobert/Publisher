package fiit.hipstery.publisher.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article extends AbstractEntity {

    protected String title;

    @ManyToOne
    protected Publisher publisher;

    @ManyToMany
    protected List<Category> categories;

    @OneToMany(mappedBy = "article", cascade=CascadeType.PERSIST)
    protected List<Comment> comments;

    @Type(type="text")
    protected String content;

    protected int likeCount;

    @Transient
    protected boolean liked;

    @Transient
    protected List<AppUser> authors;

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

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public List<AppUser> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AppUser> authors) {
        this.authors = authors;
    }
}
