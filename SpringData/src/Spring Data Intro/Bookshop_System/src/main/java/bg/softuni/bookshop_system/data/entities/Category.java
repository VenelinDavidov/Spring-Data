package bg.softuni.bookshop_system.data.entities;

import bg.softuni.bookshop_system.data.entities.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category  extends BaseEntity {

    @Column(nullable = false)
    private String  name;


    public Category() {
    }


    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // If we get duplicate categories,  then we override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Category category = (Category) o;
        return Objects.equals (name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode (name);
    }
}
