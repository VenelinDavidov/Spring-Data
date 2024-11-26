package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.DeviceSeedDto;
import softuni.exam.models.dto.xml.DeviceSeedRoodDto;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.enums.DeviceType;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final String PATH_FILE = "src/main/resources/files/xml/devices.xml";

    private final DeviceRepository deviceRepository;
    private final SaleRepository saleRepository;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public DeviceServiceImpl(DeviceRepository deviceRepository, SaleRepository saleRepository, Gson gson, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.deviceRepository = deviceRepository;
        this.saleRepository = saleRepository;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_FILE));
    }

    @Override
    public String importDevices() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        DeviceSeedRoodDto deviceSeedRoodDto =
                this.xmlParser.fromFile(PATH_FILE, DeviceSeedRoodDto.class);

        for (DeviceSeedDto deviceSeedDto : deviceSeedRoodDto.getDeviceSeedDtoList()) {

            Optional<Device> optionalDevice =
                    this.deviceRepository
                            .findByBrandAndModel(deviceSeedDto.getBrand(), deviceSeedDto.getModel());

            Optional<Sale> optionalSale =
                    this.saleRepository
                            .findById(deviceSeedDto.getSaleId());

            if (!this.validationUtil.isValid(deviceSeedDto) || optionalDevice.isPresent() || optionalSale.isEmpty()) {
                sb.append("Invalid device").append(System.lineSeparator());
                continue;
            }

            Device device = this.modelMapper.map(deviceSeedDto, Device.class);

            DeviceType deviceType = DeviceType.valueOf(deviceSeedDto.getDeviceType());
            device.setDeviceType(deviceType);
            device.setSale(optionalSale.get());
            this.deviceRepository.saveAndFlush(device);

            sb.append(String.format("Successfully imported device of type %s with brand %s",
                            device.getDeviceType(),
                            device.getBrand()))
                    .append(System.lineSeparator());
        }


        return sb.toString().trim();
    }

    @Override
    public String exportDevices() {
        return this.deviceRepository
                .findAllByDeviceTypeAndPriceLessThanAndStorageIsGreaterThanEqualOrderByBrand(DeviceType.SMART_PHONE, 1000, 128)
                .stream()
                .map(device -> String.format("Device brand: %s\n" +
                                "   *Model: %s\n" +
                                "   **Storage: %d\n" +
                                "   ***Price: %.2f\n",
                        device.getBrand(),
                        device.getModel(),
                        device.getStorage(),
                        device.getPrice()))
                .collect(Collectors.joining());
    }
}
