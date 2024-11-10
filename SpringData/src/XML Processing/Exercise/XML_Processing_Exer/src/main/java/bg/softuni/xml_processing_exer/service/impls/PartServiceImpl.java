package bg.softuni.xml_processing_exer.service.impls;

import bg.softuni.xml_processing_exer.data.entities.Part;
import bg.softuni.xml_processing_exer.data.entities.Supplier;
import bg.softuni.xml_processing_exer.data.repositories.PartRepository;
import bg.softuni.xml_processing_exer.data.repositories.SupplierRepository;
import bg.softuni.xml_processing_exer.service.PartService;
import bg.softuni.xml_processing_exer.service.dtos.imports.PartSeedDto;
import bg.softuni.xml_processing_exer.service.dtos.imports.PartSeedRoodDto;
import bg.softuni.xml_processing_exer.util.ValidationService;
import bg.softuni.xml_processing_exer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/import/parts.xml";



    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationService validationService, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedParts() throws JAXBException, FileNotFoundException {

        if (this.partRepository.count() == 0) {
            PartSeedRoodDto partSeedRoodDto = this.xmlParser.parse(PartSeedRoodDto.class, FILE_IMPORT_PATH);

            for (PartSeedDto partSeedDto : partSeedRoodDto.getPartSeedDtoList()) {
                if (!this.validationService.isValid(partSeedDto)) {
                    this.validationService.getViolations(partSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
//                    System.out.println("Invalid data");


                    continue;
                }

                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());
                this.partRepository.saveAndFlush(part);
            }
        }
    }

    // create methods random from supplier
    private Supplier getRandomSupplier() {

        return this.supplierRepository
                .findById(ThreadLocalRandom
                        .current()
                        .nextLong(1, this.supplierRepository.count() + 1))
                .get();
    }
}
