package sl.newpoi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author 舒露
 */
@Entity
public class Person {
    @Id
    private String id;
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
