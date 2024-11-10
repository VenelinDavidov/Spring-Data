package bg.softuni.xml_processing_exer.service.impls;

import bg.softuni.xml_processing_exer.data.entities.Customer;
import bg.softuni.xml_processing_exer.data.repositories.CustomerRepository;
import bg.softuni.xml_processing_exer.service.CustomerService;
import bg.softuni.xml_processing_exer.service.dtos.CustomerSeedRootDto;
import bg.softuni.xml_processing_exer.service.dtos.export.CustomerOrderedDto;
import bg.softuni.xml_processing_exer.service.dtos.export.CustomerOrderedRootDto;
import bg.softuni.xml_processing_exer.service.dtos.export.CustomerTotalSalesDto;
import bg.softuni.xml_processing_exer.service.dtos.export.CustomerTotalSalesRootDto;
import bg.softuni.xml_processing_exer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/import/customers.xml";
    private static final String FILE_EXPORT_ORDERED_CUSTOMER_PATH = "src/main/resources/xml/export/ordered-customers.xml";
    private static final String FILE_EXPORT_CUSTOMER_BOUGHT_CAR_PATH = "src/main/resources/xml/export/customers-total-sales.xml";

    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCustomers() throws JAXBException, FileNotFoundException {

        if (this.customerRepository.count() == 0) {

            CustomerSeedRootDto customerSeedRootDto = this.xmlParser.parse(CustomerSeedRootDto.class, FILE_IMPORT_PATH);
            customerSeedRootDto.getCustomerSeedDtoList().forEach(c ->
                    this.customerRepository
                            .saveAndFlush(this.modelMapper.map(c, Customer.class)));


        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {

        List<CustomerOrderedDto> customerOrderedDtos = this.customerRepository
                .findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedDto.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDto customerOrderedRootDto = new CustomerOrderedRootDto();
        customerOrderedRootDto.setCustomerOrderedDtoList(customerOrderedDtos);

        this.xmlParser.exportToFile(CustomerOrderedRootDto.class, customerOrderedRootDto, FILE_EXPORT_ORDERED_CUSTOMER_PATH);


    }

    @Override
    public void exportCustomersWithBoughtCars() throws JAXBException {

        List<CustomerTotalSalesDto> collect = this.customerRepository.findAllWithBoughtCars()
                .stream()
                .map(customer -> {
                    CustomerTotalSalesDto customerTotalSalesDto = new CustomerTotalSalesDto();
                    customerTotalSalesDto.setFullName(customer.getName());
                    customerTotalSalesDto.setBoughtCars(customer.getSales().size());

                    double spentMoney = customer.getSales()
                            .stream()
                            .mapToDouble(s -> s.getCar().getParts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum() * s.getDiscount())
                            .sum();

                    customerTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));

                    return customerTotalSalesDto;
                })
                .sorted(Comparator.comparing(CustomerTotalSalesDto::getBoughtCars).reversed()
                        .thenComparing(CustomerTotalSalesDto::getSpentMoney).reversed())
                .collect(Collectors.toList());

        CustomerTotalSalesRootDto customerTotalSalesRootDto = new CustomerTotalSalesRootDto();
        customerTotalSalesRootDto.setCustomerTotalSalesDto(collect);

        this.xmlParser.exportToFile(CustomerTotalSalesRootDto.class, customerTotalSalesRootDto, FILE_EXPORT_CUSTOMER_BOUGHT_CAR_PATH);
    }
}
