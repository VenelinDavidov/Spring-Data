package bg.softuni.modelmapperexer.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends  BaseEntity{

    private LocalDate time;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToMany

    private Set <Game> games;

}
