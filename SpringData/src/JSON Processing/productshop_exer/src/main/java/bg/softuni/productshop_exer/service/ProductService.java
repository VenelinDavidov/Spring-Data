package bg.softuni.productshop_exer.service;

import bg.softuni.productshop_exer.service.dtos.export.ProductInRangeDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

     void seedProducts() throws FileNotFoundException;

     List<ProductInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to);

     void printAllProductsInRange(BigDecimal from, BigDecimal to);
}
