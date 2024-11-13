package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private static final String FILE_PATH = "src/main/resources/files/json/constellations.json";

    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }





    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        ConstellationSeedDto[] constellationSeedDtos =
                this.gson.fromJson(new FileReader(FILE_PATH), ConstellationSeedDto[].class);

        for (ConstellationSeedDto constellationSeedDto : constellationSeedDtos) {
            Optional<Constellation> optional =
                    this.constellationRepository.findByName(constellationSeedDto.getName());

            if (!this.validationUtil.isValid(constellationSeedDto) || optional.isPresent()) {
                sb.append("Invalid constellation").append(System.lineSeparator());
                continue;
            }

            Constellation constellation = this.modelMapper.map(constellationSeedDto, Constellation.class);
            this.constellationRepository.saveAndFlush(constellation);

            sb.append(String.format("Successfully imported constellation %s - %s",
                    constellation.getName(),
                    constellation.getDescription()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}