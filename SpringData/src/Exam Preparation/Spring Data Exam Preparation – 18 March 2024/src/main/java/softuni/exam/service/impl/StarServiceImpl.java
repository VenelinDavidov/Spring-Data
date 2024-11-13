package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.StarSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;


import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {

    private static final String FILE_PATH = "src/main/resources/files/json/stars.json";

    private final StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importStars() throws IOException {

        StringBuilder sb = new StringBuilder();
        StarSeedDto[] starSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), StarSeedDto[].class);

        for (StarSeedDto starSeedDto : starSeedDtos) {
            Optional<Star> optional = this.starRepository.findByName(starSeedDto.getName());
            if (!this.validationUtil.isValid(starSeedDto) || optional.isPresent()) {
                sb.append("Invalid star").append(System.lineSeparator());
                continue;
            }

            Star star = this.modelMapper.map(starSeedDto, Star.class);

            StarType starType = StarType.valueOf(starSeedDto.getStarType());
            star.setStarType(starType);

            Constellation constellation = this.constellationRepository
                    .getById(starSeedDto.getConstellation());
            star.setConstellation(constellation);

            this.starRepository.saveAndFlush(star);
            sb.append(String.format("Successfully imported star %s - %.2f light years",
                            star.getName(),
                            star.getLightYears()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }


    @Override
    public String exportStars() {
        return this.starRepository
                .findAllByStarTypeOrderByLightYears()
                .stream()
                .map(s -> String.format("Star: %s\n" +
                                "   *Distance: %.2f light years\n" +
                                "   **Description: %s\n" +
                                "   ***Constellation: %s\n",
                        s.getName(), s.getLightYears(), s.getDescription(), s.getConstellation().getName()))
                .collect(Collectors.joining());

    }
}
