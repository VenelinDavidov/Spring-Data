package Entitites.ex_03;

import Entitites.BaseEntity;
import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity
@Table(name = "information")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Information extends BaseEntity {


    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone")
    private String phone;


}
