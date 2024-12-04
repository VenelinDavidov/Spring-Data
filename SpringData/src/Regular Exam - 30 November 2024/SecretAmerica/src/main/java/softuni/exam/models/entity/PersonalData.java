package softuni.exam.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "personal_datas")
public class PersonalData extends BaseEntity {

    @Column(name = "age")
    private int age;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "card_number",nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "gender")
    private char gender;

    @OneToOne(mappedBy = "personalData")
    private Visitor visitor;
}
