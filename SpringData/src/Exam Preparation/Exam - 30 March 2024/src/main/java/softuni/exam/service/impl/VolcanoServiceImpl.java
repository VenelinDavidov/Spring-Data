package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.CountryService;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String VOLCANO_FILE_NAME = "src/main/resources/files/json/volcanoes.json";
    private final VolcanoRepository volcanoRepository;
    private final CountryService countryService;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, CountryService countryService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.volcanoRepository = volcanoRepository;
        this.countryService = countryService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANO_FILE_NAME));
    }

    @Override
    public String importVolcanoes() throws IOException {

        StringBuilder sb = new StringBuilder();
        VolcanoImportDto[] volcanoImportDtos = this.gson.fromJson(readVolcanoesFileContent(), VolcanoImportDto[].class);

        for (VolcanoImportDto volcanoImportDto : volcanoImportDtos) {

            Optional<Volcano> optional = this.volcanoRepository.findByName(volcanoImportDto.getName());

            if (!this.validationUtil.isValid(volcanoImportDto) || optional.isPresent()) {
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = this.modelMapper.map(volcanoImportDto, Volcano.class);

            Country country = this.countryService.getCountryById(volcanoImportDto.getCountry()).get();
            volcano.setCountry(country);

            VolcanoType volcanoType = VolcanoType.valueOf(volcanoImportDto.getVolcanoType());
            volcano.setVolcanoType(volcanoType);
//            volcano.setCountry(this.countryRepository.findById(volcanoImportDto.getCountry()).get());

            this.volcanoRepository.saveAndFlush(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s",
                            volcano.getName(),
                            volcano.getVolcanoType().toString()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

//    @Override
//    public Volcano findVolcanoById(Long volcanoId) {
//        return this.volcanoRepository.getById(volcanoId);
//    }


    @Override
    public String exportVolcanoes() {


        return  this.volcanoRepository.findByActiveTrueAndElevationGreaterThanAndLastEruptionIsNotNullOrderByElevationDesc(3000)
                .stream()
                .map(volcano -> String.format("Volcano: %s\n" +
                                "   *Located in: %s\n" +
                                "   **Elevation: %d\n" +
                                "   ***Last eruption on: %s\n",
                        volcano.getName(),
                        volcano.getCountry().getName(),
                        volcano.getElevation(),
                        volcano.getLastEruption()))
                .collect(Collectors.joining());
    }
}