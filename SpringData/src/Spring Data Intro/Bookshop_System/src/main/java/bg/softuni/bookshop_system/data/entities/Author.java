package bg.softuni.bookshop_system.data.entities;

import bg.softuni.bookshop_system.data.entities.base.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    @Column(name = "fist_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private Set <Book> books;

    // constructor
    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public Set <Book> getBooks() {
        return books;
    }

    public void setBooks(Set <Book> books) {
        this.books = books;
    }
}
