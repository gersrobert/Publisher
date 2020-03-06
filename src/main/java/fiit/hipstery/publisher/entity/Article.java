package fiit.hipstery.publisher.entity;

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
}
