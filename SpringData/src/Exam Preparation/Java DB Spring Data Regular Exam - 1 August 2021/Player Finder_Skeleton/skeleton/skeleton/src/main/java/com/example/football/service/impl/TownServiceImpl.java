package com.example.football.service.impl;

import com.example.football.models.dto.gsons.TownImportDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class TownServiceImpl implements TownService {

    private static final String FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        TownImportDto[] townImportDtos =
                this.gson.fromJson(Files.readString(Path.of(FILE_PATH)), TownImportDto[].class);

        for (TownImportDto townImportDto : townImportDtos) {

            Optional<Town> optionalTown = this.townRepository.findByName(townImportDto.getName());

            if(!this.validationUtil.isValid(optionalTown) || optionalTown.isPresent()) {
                stringBuilder.append("Invalid Town").append(System.lineSeparator());
                continue;
            }
            Town town = this.modelMapper.map(townImportDto, Town.class);
            this.townRepository.save(town);
            stringBuilder.append(String.format("Successfully imported Town %s - %d",
                    town.getName(),
                    town.getPopulation()))
                    .append(System.lineSeparator());

        }

        return stringBuilder.toString().trim();
    }
}
