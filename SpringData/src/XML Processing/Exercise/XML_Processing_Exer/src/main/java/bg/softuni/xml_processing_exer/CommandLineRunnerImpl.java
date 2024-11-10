package bg.softuni.xml_processing_exer;

import bg.softuni.xml_processing_exer.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;


    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }


    @Override
    public void run(String... args) throws Exception {

        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();
        this.saleService.seedSales();

//        this.customerService.exportOrderedCustomers();
//        this.carService.exportToyotaCars();
//        this.supplierService.exportLocalSuppliers();
//        this.carService.exportCarsAndParts();
        this.customerService.exportCustomersWithBoughtCars();
        this.saleService.exportSales();
    }
}
