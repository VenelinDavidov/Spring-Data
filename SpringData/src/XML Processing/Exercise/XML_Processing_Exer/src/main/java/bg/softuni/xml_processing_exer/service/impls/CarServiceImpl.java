package bg.softuni.xml_processing_exer.service.impls;

import bg.softuni.xml_processing_exer.data.entities.Car;
import bg.softuni.xml_processing_exer.data.entities.Part;
import bg.softuni.xml_processing_exer.data.repositories.CarRepository;
import bg.softuni.xml_processing_exer.data.repositories.PartRepository;
import bg.softuni.xml_processing_exer.service.CarService;
import bg.softuni.xml_processing_exer.service.dtos.CarSeedDto;
import bg.softuni.xml_processing_exer.service.dtos.CarSeedRoodDto;
import bg.softuni.xml_processing_exer.service.dtos.export.*;
import bg.softuni.xml_processing_exer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {


    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/import/cars.xml";
    private static final String FILE_EXPORT_TOYOTA_PATH = "src/main/resources/xml/export/toyota-cars.xml";
    private static final String  FILE_EXPORT_CARS_AND_PARTS_PATH = "src/main/resources/xml/export/cars-and-parts.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;


    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCars() throws JAXBException, FileNotFoundException {
        if (this.carRepository.count() == 0) {

            CarSeedRoodDto carsSeedDto = this.xmlParser.parse(CarSeedRoodDto.class, FILE_IMPORT_PATH);

            for (CarSeedDto carSeedDto : carsSeedDto.getCarSeedDtoList()) {
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(getRandomPart());

                this.carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public void exportToyotaCars() throws JAXBException, FileNotFoundException {


        List<CarToyotaDto> carToyotaDtos = this.carRepository.findAllByMakeOrderByTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car, CarToyotaDto.class))
                .collect(Collectors.toList());

        CarToyotaRootDto carToyotaRootDto = new CarToyotaRootDto();
        carToyotaRootDto.setCarToyotaDtoList(carToyotaDtos);

        this.xmlParser.exportToFile(CarToyotaRootDto.class, carToyotaRootDto, FILE_EXPORT_TOYOTA_PATH);

    }

    @Override
    public void exportCarsAndParts() throws JAXBException, FileNotFoundException {

        List<CarAndPartDto> carAndPartDtos = this.carRepository.findAll()
                .stream()
                .map(car -> {
                    CarAndPartDto carAndPartDto = this.modelMapper.map(car, CarAndPartDto.class);

                    PartRootDto partRootDto = new PartRootDto();

                    List<PartDto> partDtos = car.getParts()
                            .stream()
                            .map(part -> this.modelMapper.map(part, PartDto.class))
                            .collect(Collectors.toList());

                    partRootDto.setPartDtoList(partDtos);
                    carAndPartDto.setPartRootDto(partRootDto);

                    return carAndPartDto;
                })
                .collect(Collectors.toList());

        CarAndPartsRootDto carAndPartsRootDto = new CarAndPartsRootDto();
        carAndPartsRootDto.setCarAndPartDtos(carAndPartDtos);

        this.xmlParser.exportToFile(CarAndPartsRootDto.class, carAndPartsRootDto, FILE_EXPORT_CARS_AND_PARTS_PATH);
    }


    // methods random
    private Set<Part> getRandomPart() {

        Set<Part> parts = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(2, 4);

        for (int i = 0; i < count; i++) {
            parts.add(this.partRepository
                    .findById(ThreadLocalRandom.current()
                            .nextLong(1, this.partRepository.count() + 1))
                    .get());
        }
        return parts;
    }
}
