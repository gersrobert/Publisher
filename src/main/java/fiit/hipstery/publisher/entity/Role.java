package fiit.hipstery.publisher.entity;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractEntity {

    protected String name;

    public String getName() {
        return name;
    }
}
