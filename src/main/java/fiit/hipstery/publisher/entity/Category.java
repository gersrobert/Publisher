package fiit.hipstery.publisher.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {
        @Index(columnList = "name")
})
public class Category extends AbstractEntity {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
