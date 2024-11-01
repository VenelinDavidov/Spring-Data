package org.modelmapperexercise.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game extends BaseEntity{

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private double price;
    @Column
    private double size;
    @Column
    private String trailer;
    @Column
    private String thumbnail;
    @Column
    private String description;
    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(price, game.price) == 0 && Double.compare(size, game.size) == 0 && Objects.equals(title, game.title) && Objects.equals(trailer, game.trailer) && Objects.equals(thumbnail, game.thumbnail) && Objects.equals(description, game.description) && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, size, trailer, thumbnail, description, releaseDate);
    }
}
