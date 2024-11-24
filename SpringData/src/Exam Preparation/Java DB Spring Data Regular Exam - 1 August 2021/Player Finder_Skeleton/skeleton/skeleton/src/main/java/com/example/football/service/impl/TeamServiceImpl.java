package com.example.football.service.impl;

import com.example.football.models.dto.gsons.TeamImportDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private static final String PATH_FILE = "src/main/resources/files/json/teams.json";

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_FILE));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        TeamImportDto[] teamImportDtos = this.gson.fromJson(readTeamsFileContent(), TeamImportDto[].class);
        for (TeamImportDto teamImportDto : teamImportDtos) {

            Optional<Team> optionalTeam = this.teamRepository.findByName(teamImportDto.getName());

            if (!this.validationUtil.isValid(teamImportDto) || optionalTeam.isPresent()) {
                sb.append("Invalid Team").append(System.lineSeparator());
                continue;
            }

            Team team = this.modelMapper.map(teamImportDto, Team.class);
            Town town = this.townRepository.findByName(teamImportDto.getTownName()).get();
            team.setTown(town);
            this.teamRepository.saveAndFlush(team);

            sb.append(String.format("Successfully imported %s - %d",
                    team.getName(),
                    team.getFanBase()))
                    .append(System.lineSeparator());

        }
        return sb.toString().trim();
    }
}
