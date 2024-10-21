package Entitites.ex_04;

import Entitites.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "medicament")
public class Medicament extends BaseEntity {

    @Column
    private  String  name;

    @ManyToMany(mappedBy = "medicaments")
   private Set <Diagnose> diagnoses;


}
