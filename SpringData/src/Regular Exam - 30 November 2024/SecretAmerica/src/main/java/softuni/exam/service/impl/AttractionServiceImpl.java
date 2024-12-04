package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.AttractionsSeedRoodDto;
import softuni.exam.models.entity.Attraction;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AttractionServiceImpl implements AttractionService {

    private static final String FILE_PATH = "src/main/resources/files/json/attractions.json";

    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public AttractionServiceImpl(AttractionRepository attractionRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAttractions() throws IOException {
        StringBuilder sb = new StringBuilder();

        AttractionsSeedRoodDto[] attractionsSeedRoodDtos = this.gson.fromJson(Files.readString(Path.of(FILE_PATH)), AttractionsSeedRoodDto[].class);

        for (AttractionsSeedRoodDto attractionsSeedRoodDto : attractionsSeedRoodDtos) {
            Optional<Attraction> optionalAttraction = this.attractionRepository.findByName(attractionsSeedRoodDto.getName());

            if(!this.validationUtil.isValid(attractionsSeedRoodDto) || optionalAttraction.isPresent()) {

                sb.append("Invalid attraction").append(System.lineSeparator());
                continue;
            }

            Attraction attraction = this.modelMapper.map(attractionsSeedRoodDto, Attraction.class);
            Country country = this.countryRepository.findById(attractionsSeedRoodDto.getCountry()).get();
            attraction.setCountry(country);

            this.attractionRepository.saveAndFlush(attraction);

            sb.append(String.format("Successfully imported attraction %s",
                    attraction.getName()))
                    .append(System.lineSeparator());
        }



        return sb.toString().trim();
    }

    @Override
    public String exportAttractions() {
        return this.attractionRepository.findAttractionByTypeAndElevationMoreThanOrEqualTo300()
                .stream()
                .map(attraction -> String.format("Attraction with ID%d:\n" +
                        "***%s - %s at an altitude of %dm. somewhere in %s.\n",
                        attraction.getId(),
                        attraction.getName(),
                        attraction.getDescription(),
                        attraction.getElevation(),
                        attraction.getCountry().getName()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}