package bg.softuni.modelmapperexer.service.dtos;

public class CartItemDTO {

    private String title;

    public CartItemDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
