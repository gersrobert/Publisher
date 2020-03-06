package fiit.hipstery.publisher.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Publisher extends AbstractEntity {

    protected String name;

    @OneToMany
    protected List<AppUser> authors;

    @OneToMany
    protected List<Article> articles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppUser> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AppUser> authors) {
        this.authors = authors;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
