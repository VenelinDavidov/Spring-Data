package bg.softuni.productshop_exer.service.dtos.export;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UserSoldProductsDto implements Serializable {

    //Get all users who have at least 1 item sold with a buyer.
    // Order them by last name, then by first name. Select the person's first and last name


    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private List<ProductSoldDto> soldProducts;






    // getter and setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductSoldDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSoldDto> soldProducts) {
        this.soldProducts = soldProducts;
    }

}
