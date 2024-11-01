package bg.softuni.modelmapperexer.service;

import bg.softuni.modelmapperexer.service.dtos.CartItemDTO;

public interface ShoppingCartService {

    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
