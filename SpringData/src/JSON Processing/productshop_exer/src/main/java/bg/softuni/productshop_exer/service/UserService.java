package bg.softuni.productshop_exer.service;

import bg.softuni.productshop_exer.service.dtos.export.UserAndProductDto;
import bg.softuni.productshop_exer.service.dtos.export.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDto> getAllUsersAndSoldItems();

    void printAllUsersAndSoldItems();

    UserAndProductDto getUserAndProductDto();

    void  printGetUserAndProductDto();
}
