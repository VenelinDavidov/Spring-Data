package Entitites.ex_05;

import Entitites.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "fist_name")
    private String  firstName;

    @Column(name = "last_name")
    private String  lastName;

    @Column
    private String  email;

    @Column
    private String  password;

    @OneToMany(mappedBy = "user")
    private Set <BillingDetails> billingDetails;
}
