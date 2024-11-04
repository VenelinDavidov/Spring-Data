package bg.softuni.productshop_exer.service.impl;

import bg.softuni.productshop_exer.data.entities.User;
import bg.softuni.productshop_exer.data.repositories.UserRepository;
import bg.softuni.productshop_exer.service.UserService;
import bg.softuni.productshop_exer.service.dtos.export.*;
import bg.softuni.productshop_exer.service.dtos.imports.UserSeedDto;
import bg.softuni.productshop_exer.util.ValidationService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //Constant
    private static final String FILE_PATH = "src/main/resources/json/users.json";


    //Bean
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationService validationService;


    //constructor
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, Gson gson, ValidationService validationService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationService = validationService;
    }


    @Override
    public void seedUsers() throws FileNotFoundException {

        if (this.userRepository.count() == 0) {

            UserSeedDto[] userSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);

            for (UserSeedDto userSeedDto : userSeedDtos) {
                if (!this.validationService.isValid(userSeedDto)) {

                    this.validationService.getViolations(userSeedDto)
                            .forEach(violation -> System.out.println(violation.getMessage()));

                    continue;
                }

                this.userRepository
                        .saveAndFlush(this.modelMapper.map(userSeedDto, User.class));
            }
        }
    }


    //Query 2 – Successfully Sold Products
    @Override
    public List<UserSoldProductsDto> getAllUsersAndSoldItems() {

        return this.userRepository.findAllBy()
                .stream()
                .filter(u ->
                        u.getSold().stream().anyMatch(p -> p.getBuyer() != null))
                .map(u -> {

                    UserSoldProductsDto userDto = this.modelMapper.map(u, UserSoldProductsDto.class);

                    List<ProductSoldDto> soldProductsDto = u.getSold()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductSoldDto.class))
                            .collect(Collectors.toList());

                    userDto.setSoldProducts(soldProductsDto);

                    return userDto;
                })
                .sorted(Comparator.comparing(UserSoldProductsDto::getLastName).thenComparing(UserSoldProductsDto::getFirstName))
                .toList();

    }


    // print method
    @Override
    public void printAllUsersAndSoldItems() {
        System.out.println(this.gson.toJson(this.getAllUsersAndSoldItems()));

    }


    //#Query 4 – Users and Products
    @Override
    public UserAndProductDto getUserAndProductDto() {

        UserAndProductDto userAndProductDto = new UserAndProductDto();

        List<UserSoldDto> userSoldDtos = this.userRepository.findAll()
                .stream()
                .filter(user -> !user.getSold().isEmpty())
                .map(user -> {
                    UserSoldDto userSoldDto = this.modelMapper.map(user, UserSoldDto.class);

                    ProductSoldByUserDto productSoldByUserDto = new ProductSoldByUserDto();

                    List<ProductInfoDto> productInfoDto = user.getSold()
                            .stream()
                            .map(product -> this.modelMapper.map(product, ProductInfoDto.class))
                            .collect(Collectors.toList());

                    productSoldByUserDto.setProducts(productInfoDto);
                    productSoldByUserDto.setCount(productInfoDto.size());

                    userSoldDto.setSoldProducts(productSoldByUserDto);

                    return userSoldDto;
                })
                .sorted((a, b) -> {
                            int countA = a.getSoldProducts().getCount();
                            int countB = b.getSoldProducts().getCount();
                            return Integer.compare(countB,countA);
                })
                .collect(Collectors.toList());

        userAndProductDto.setUsers(userSoldDtos);
        userAndProductDto.setUsersCount(userSoldDtos.size());

        return userAndProductDto;
    }

    //method print
    @Override
    public void printGetUserAndProductDto() {

        System.out.println(this.gson.toJson(this.getUserAndProductDto()));
    }
}
