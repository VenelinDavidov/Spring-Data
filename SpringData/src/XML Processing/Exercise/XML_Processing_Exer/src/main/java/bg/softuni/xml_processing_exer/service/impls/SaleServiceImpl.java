package bg.softuni.xml_processing_exer.service.impls;

import bg.softuni.xml_processing_exer.data.entities.Car;
import bg.softuni.xml_processing_exer.data.entities.Customer;
import bg.softuni.xml_processing_exer.data.entities.Part;
import bg.softuni.xml_processing_exer.data.entities.Sale;
import bg.softuni.xml_processing_exer.data.repositories.CarRepository;
import bg.softuni.xml_processing_exer.data.repositories.CustomerRepository;
import bg.softuni.xml_processing_exer.data.repositories.SaleRepository;
import bg.softuni.xml_processing_exer.service.SaleService;
import bg.softuni.xml_processing_exer.service.dtos.export.CarDto;
import bg.softuni.xml_processing_exer.service.dtos.export.SaleDiscountsDto;
import bg.softuni.xml_processing_exer.service.dtos.export.SaleDiscountsRootDto;
import bg.softuni.xml_processing_exer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final List<Double> discounts = List.of(1.0, 0.95, 0.90, 0.85, 0.80, 0.70, 0.60, 0.50);
    private static final String  FILE_SALE_DISCOUNT_PATH = "src/main/resources/xml/export/sales-discounts.xml";



    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;



    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }



    @Override
    public void seedSales() {

        if (this.saleRepository.count() == 0) {

            for (int i = 0; i < 50; i++) {

                Sale sale = new Sale();

                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                this.saleRepository.saveAndFlush(sale);

            }


        }
    }




    @Override
    public void exportSales() throws JAXBException {
        List<SaleDiscountsDto> saleDiscountDtos = this.saleRepository
                .findAll()
                .stream()
                .map(s -> {
                    SaleDiscountsDto saleDiscountDto = this.modelMapper.map(s, SaleDiscountsDto.class);
                    CarDto car = this.modelMapper.map(s.getCar(), CarDto.class);

                    saleDiscountDto.setCarDto(car);
                    saleDiscountDto.setCustomerName(s.getCustomer().getName());
                    saleDiscountDto.setPrice(s.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
                    saleDiscountDto.setPriceWithDiscount(saleDiscountDto.getPrice().multiply(BigDecimal.valueOf(s.getDiscount())));
                    return saleDiscountDto;
                })
                .collect(Collectors.toList());

        SaleDiscountsRootDto saleDiscountsRootDto = new SaleDiscountsRootDto();
        saleDiscountsRootDto.setSaleDiscountsDto(saleDiscountDtos);

        this.xmlParser.exportToFile(SaleDiscountsRootDto.class, saleDiscountsRootDto, FILE_SALE_DISCOUNT_PATH);
    }







    private double getRandomDiscount() {
        return discounts
                .get(ThreadLocalRandom.current().nextInt(1, discounts.size()));
    }


    private Customer getRandomCustomer() {
        return this.customerRepository
                .findById(ThreadLocalRandom.current()
                        .nextLong(1, this.customerRepository.count() + 1))
                .get();
    }

    private Car getRandomCar() {
        return this.carRepository
                .findById(ThreadLocalRandom.current()
                        .nextLong(1, this.carRepository.count() + 1))
                .get();
    }
}
