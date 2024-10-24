package bg.softuni.bookshop_system.data.entities;

import bg.softuni.bookshop_system.data.entities.base.BaseEntity;
import bg.softuni.bookshop_system.data.entities.enums.AgeRestriction;
import bg.softuni.bookshop_system.data.entities.enums.EditionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(length = 50, nullable = false)
    @NotNull(message = "Title cannot be null, enter value")
    private String title;

    @Column(length = 1000)
    @Length(min = 1, max = 1000, message = "Description is not in valid length diapason")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Min (value = 5)
    @Max (value = 5000)
    @NotNull
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "book_categories",
    joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set <Category> categories;


    public Book(String title, EditionType editionType, BigDecimal price, int copies, LocalDate releaseDate, AgeRestriction ageRestriction, Author author, Set <Category> categories) {
        this.title = title;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.author = author;
        this.categories = categories;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public Author getAuthor() {
        return author;
    }

    public Set <Category> getCategories() {
        return categories;
    }
}
