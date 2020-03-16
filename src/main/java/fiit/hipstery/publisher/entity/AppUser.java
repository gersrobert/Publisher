package fiit.hipstery.publisher.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUser extends AbstractEntity {

    @Column(unique = true)
    protected String userName;

    protected String firstName;

    protected String lastName;

    @ManyToOne
    protected Publisher publisher;

    @ManyToMany
    protected List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    protected List<Category> subscribedCategories;

    @ManyToMany(fetch = FetchType.LAZY)
    protected List<Article> likedArticles;

    @ManyToMany(fetch = FetchType.LAZY)
    protected List<Article> readArticles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Category> getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(List<Category> subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }
}
