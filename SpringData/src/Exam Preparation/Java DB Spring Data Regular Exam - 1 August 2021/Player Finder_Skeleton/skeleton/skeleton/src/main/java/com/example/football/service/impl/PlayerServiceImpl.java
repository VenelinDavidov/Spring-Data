package com.example.football.service.impl;

import com.example.football.models.dto.xml.PlayerSeedDto;
import com.example.football.models.dto.xml.PlayersSeedRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.models.entity.enums.PlayerPosition;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final String PATH_INPUT = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, Gson gson, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;

        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(PATH_INPUT)));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        PlayersSeedRootDTO playersSeedRootDTO = this.xmlParser.fromFile( PATH_INPUT, PlayersSeedRootDTO.class);


        for (PlayerSeedDto playerSeedDTO : playersSeedRootDTO.getPlayerSeedDtoList()) {

            Optional<Player> playerOptional = this.playerRepository.findByEmail(playerSeedDTO.getEmail());

            if (!this.validationUtil.isValid(playerSeedDTO) || playerOptional.isEmpty()) {
                stringBuilder.append("Invalid Player").append(System.lineSeparator());
                continue;
            }

            Player player = this.modelMapper.map(playerSeedDTO, Player.class);

            PlayerPosition position = PlayerPosition.valueOf(playerSeedDTO.getPosition());
            Town town = this.townRepository.findByName(playerSeedDTO.getTown().getName()).get();
            Team team = this.teamRepository.findByName(playerSeedDTO.getTeam().getName()).get();

            player.setPosition(position);
            player.setTown(town);
            player.setTeam(team);

            this.playerRepository.saveAndFlush(player);
            stringBuilder.append(String.format("Successfully imported Player %s %s - %s",
                    player.getFirstName(),
                    player.getLastName(),
                    player.getPosition()))
                    .append(System.lineSeparator());
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        LocalDate before = LocalDate.of(2003, 1, 1);
        LocalDate after = LocalDate.of(1995, 1, 1);

        List<Player> players = this.playerRepository
                .findByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(after, before);

        return players
                .stream()
                .map(Player::toString)
                .collect(Collectors.joining("\n"));


    }

}
