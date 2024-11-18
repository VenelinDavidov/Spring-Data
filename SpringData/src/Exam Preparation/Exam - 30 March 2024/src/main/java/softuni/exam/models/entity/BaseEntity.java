package softuni.exam.models.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    // constructor
    protected BaseEntity() {
    }



    //  getter
    public long getId() {
        return id;
    }


}
