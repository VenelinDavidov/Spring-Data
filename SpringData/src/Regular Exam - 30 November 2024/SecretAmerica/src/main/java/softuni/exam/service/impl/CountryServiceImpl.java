package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.CountrySeedRoodDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {

    private static final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {

        StringBuilder sb = new StringBuilder();

        CountrySeedRoodDto[] countrySeedRoodDtos = this.gson.fromJson(readCountryFileContent(), CountrySeedRoodDto[].class);

        for (CountrySeedRoodDto countrySeedRoodDto : countrySeedRoodDtos) {

            Optional<Country> optionalCountry = this.countryRepository.findByName(countrySeedRoodDto.getName());

            if (!this.validationUtil.isValid(countrySeedRoodDto) || optionalCountry.isPresent()) {

                sb.append("Invalid country").append(System.lineSeparator());
                continue;
            }
            Country country = this.modelMapper.map(countrySeedRoodDto, Country.class);
            this.countryRepository.saveAndFlush(country);

            sb.append(String.format("Successfully imported country %s", country.getName())).append(System.lineSeparator());

        }


        return sb.toString().trim();
    }
}
