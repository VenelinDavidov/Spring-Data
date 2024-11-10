package bg.softuni._19_xml.controllers;

import bg.softuni._19_xml.models.AddressDTO;
import bg.softuni._19_xml.models.ProductDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/address")
    public AddressDTO address() {
        return new AddressDTO("BG", "Ruse");
    }

    @GetMapping(value = "/product")
    public ProductDTO product() {
        return new ProductDTO(
            "Pen",
            BigDecimal.ONE,
            "Amazing",
            List.of("one", "two", "three")
        );
    }

    @GetMapping(value = "/products")
    @CrossOrigin(origins = "*")
    public List<ProductDTO> getAllProducts() {
        return IntStream.range(0, 100)
            .boxed()
            .map(i -> getRandomProduct())
            .collect(Collectors.toList());
    }

    private ProductDTO getRandomProduct() {
        Random random = new Random();
        return new ProductDTO(
                getRandomString(5),
                BigDecimal.valueOf(random.nextInt(1500)),
                getRandomString(50),
                List.of("one", "two", "three")
        );
    }

    private String getRandomString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
