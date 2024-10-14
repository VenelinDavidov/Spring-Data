package bg.softuni;

import orm.Annotations.Colum;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.time.LocalDate;

@Entity(name = "users")
public class User {

    // fields
    @Id
    @Colum(name = "id")
    private long id;// auto increment

    @Colum(name = "username")
    private String userName;

    @Colum(name = "age")
    private int age;

    @Colum(name = "registration")
    private LocalDate registration;

    @Colum (name = "email")
    private String email;

    public User() {

    }

    // Constructor
    public User(String userName, int age, LocalDate registrationDate) {
        this.userName = userName;
        this.age = age;
        this.registration = registrationDate;
    }




    // getter and setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
