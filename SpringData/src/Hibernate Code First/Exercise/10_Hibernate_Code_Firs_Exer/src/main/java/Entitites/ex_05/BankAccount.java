package Entitites.ex_05;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetails{

    @Column
    private String name;

    @Column(name = "swift_code")
    private String swiftCode;
}
