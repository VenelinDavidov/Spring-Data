package Entitites.ex_05;

import javax.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCard extends  BillingDetails{

    @Column
    @Enumerated(value = EnumType.STRING)
    private CardType card;

    @Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

}
