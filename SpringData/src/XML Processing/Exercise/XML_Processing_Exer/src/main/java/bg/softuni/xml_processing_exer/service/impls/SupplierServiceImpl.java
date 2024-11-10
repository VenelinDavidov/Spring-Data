package bg.softuni.xml_processing_exer.service.impls;

import bg.softuni.xml_processing_exer.data.entities.Supplier;
import bg.softuni.xml_processing_exer.data.repositories.SupplierRepository;
import bg.softuni.xml_processing_exer.service.SupplierService;
import bg.softuni.xml_processing_exer.service.dtos.export.SupplierLocalDto;
import bg.softuni.xml_processing_exer.service.dtos.export.SupplierLocalRoodDto;
import bg.softuni.xml_processing_exer.service.dtos.imports.SupplierSeedDto;
import bg.softuni.xml_processing_exer.service.dtos.imports.SupplierSeedRoodDto;
import bg.softuni.xml_processing_exer.util.ValidationService;
import bg.softuni.xml_processing_exer.util.XmlParser;


import jakarta.xml.bind.JAXBException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/import/suppliers.xml";
    private static final String FILE_EXPORT_PATH = "src/main/resources/xml/export/local-suppliers.xml";

    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationService validationService;
    private final ModelMapper modelMapper;


    //constructor
    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationService validationService, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationService = validationService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSuppliers() throws JAXBException, FileNotFoundException {

        if (this.supplierRepository.count() == 0) {
            SupplierSeedRoodDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRoodDto.class, FILE_IMPORT_PATH);

            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if (!this.validationService.isValid(supplierSeedDto)) {
                    this.validationService.getViolations(supplierSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
//                    System.out.println("Invalid supplier data");

                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }



    @Override
    public void exportLocalSuppliers() throws JAXBException, FileNotFoundException {

        List<SupplierLocalDto> supplierLocalDtos = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(supplier -> {
                    SupplierLocalDto dto = this.modelMapper.map(supplier, SupplierLocalDto.class);
                    dto.setPartCount(supplier.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SupplierLocalRoodDto supplierLocalRoodDto =  new SupplierLocalRoodDto();
        supplierLocalRoodDto.setSupplierLocalDtoList(supplierLocalDtos);

        this.xmlParser.exportToFile(SupplierLocalRoodDto.class, supplierLocalRoodDto, FILE_EXPORT_PATH);

    }


}
