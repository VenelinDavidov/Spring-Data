package bg.softuni._19_xml.models;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {

    private String name;
    private BigDecimal price;
    private String description;
    private List<String> categories;

    public ProductDTO() {}

    public ProductDTO(String name, BigDecimal price, String description, List<String> categories) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
