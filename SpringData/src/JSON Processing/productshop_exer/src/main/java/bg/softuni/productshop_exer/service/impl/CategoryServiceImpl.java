package bg.softuni.productshop_exer.service.impl;

import bg.softuni.productshop_exer.data.entities.Category;
import bg.softuni.productshop_exer.data.entities.Product;
import bg.softuni.productshop_exer.data.repositories.CategoryRepository;
import bg.softuni.productshop_exer.service.CategoryService;
import bg.softuni.productshop_exer.service.dtos.export.CategoryByProductsDto;
import bg.softuni.productshop_exer.service.dtos.imports.CategorySeedDto;
import bg.softuni.productshop_exer.util.ValidationService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    //Constant
    private static final String FILE_PATH = "src/main/resources/json/categories.json";


    //Bean

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationService validationService;
    private final ModelMapper modelMapper;

    //constructor
    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationService validationService, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationService = validationService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {

        if (this.categoryRepository.count() == 0) {
            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));
            // this.gson.fromJson(new FileReader(FILE_PATH), CategorySeedDto[].class);

            CategorySeedDto[] categorySeedDtos = this.gson.fromJson(jsonContent, CategorySeedDto[].class);
            for (CategorySeedDto categorySeedDto : categorySeedDtos) {


                if (!this.validationService.isValid(categorySeedDto)) {

                    this.validationService.getViolations(categorySeedDto)
                            .forEach(violation -> System.out.println(violation.getMessage()));

                    continue;
                }

                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }

    }


    @Override
    public List<CategoryByProductsDto> getAllCategoriesByProducts() {
        return this.categoryRepository.findAllCategoriesByProducts()
                .stream()
                .map(category -> {
                    CategoryByProductsDto dto = this.modelMapper.map(category, CategoryByProductsDto.class);

                    dto.setProductsCount(category.getProducts().size());

                    BigDecimal sum =
                            category.getProducts()
                                    .stream()
                                    .map(Product::getPrice)
                                    .reduce(BigDecimal::add).get();

                    dto.setTotalRevenue(sum);
                    dto.setAveragePrice(sum.divide(BigDecimal.valueOf(category.getProducts().size()), MathContext.DECIMAL64));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void printAllCategoriesByProducts() {
        System.out.println(this.gson.toJson(this.getAllCategoriesByProducts()));
    }
}
