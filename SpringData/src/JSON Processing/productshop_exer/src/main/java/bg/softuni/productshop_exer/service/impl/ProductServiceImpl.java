package bg.softuni.productshop_exer.service.impl;

import bg.softuni.productshop_exer.data.entities.Category;
import bg.softuni.productshop_exer.data.entities.Product;
import bg.softuni.productshop_exer.data.entities.User;
import bg.softuni.productshop_exer.data.repositories.CategoryRepository;
import bg.softuni.productshop_exer.data.repositories.ProductRepository;
import bg.softuni.productshop_exer.data.repositories.UserRepository;
import bg.softuni.productshop_exer.service.ProductService;
import bg.softuni.productshop_exer.service.dtos.export.ProductInRangeDto;
import bg.softuni.productshop_exer.service.dtos.imports.ProductSeedDto;
import bg.softuni.productshop_exer.util.ValidationService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {


    //Constant
    private static final String FILE_PATH = "src/main/resources/json/products.json";


    //Bean
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;
    private final Gson gson;


    // Constructor
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationService validationService, Gson gson) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
        this.gson = gson;
    }


    @Override
    public void seedProducts() throws FileNotFoundException {

        if (this.productRepository.count() == 0) {
            ProductSeedDto[] productSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), ProductSeedDto[].class);

            for (ProductSeedDto productSeedDto : productSeedDtos) {
                if (!this.validationService.isValid(productSeedDto)) {
                    this.validationService.getViolations(productSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));

                    continue;
                }

                Product product = this.modelMapper.map(productSeedDto, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategories());

                this.productRepository.saveAndFlush(product);

            }
        }
    }




    @Override
    public List<ProductInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to) {

        return this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(from, to)
                .stream()
                .map(p -> {
                    ProductInRangeDto dto = this.modelMapper.map(p, ProductInRangeDto.class);
                    dto.setSeller(p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return dto;
                })
                .sorted(Comparator.comparing(ProductInRangeDto::getPrice))
               // .sorted((a,b) -> a.getPrice().compareTo(b.getPrice()))
                .collect(Collectors.toList());

    }

    //Print Method
    @Override
    public void printAllProductsInRange(BigDecimal from, BigDecimal to) {
        System.out.println(this.gson.toJson(this.getAllProductsInRange(from, to)));
    }




    private Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();

        int randomCount = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < randomCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(randomId).get());
        }

        return categories;
    }





    private User getRandomUser(boolean isBayer) {

        long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);

        return isBayer && randomId % 4 == 0
                ? null
                : this.userRepository.findById(randomId).get();

    }
}
