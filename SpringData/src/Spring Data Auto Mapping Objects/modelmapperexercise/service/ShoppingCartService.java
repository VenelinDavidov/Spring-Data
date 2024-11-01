package org.modelmapperexercise.service;

import org.modelmapperexercise.service.dtos.CartItemDTO;

public interface ShoppingCartService {

    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
