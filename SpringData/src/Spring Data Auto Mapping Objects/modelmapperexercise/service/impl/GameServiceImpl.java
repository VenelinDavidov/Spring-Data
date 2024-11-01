package org.modelmapperexercise.service.impl;

import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.modelmapperexercise.data.entities.Game;
import org.modelmapperexercise.data.repositories.GameRepository;
import org.modelmapperexercise.service.GameService;
import org.modelmapperexercise.service.UserService;
import org.modelmapperexercise.service.dtos.GameAddDTO;
import org.modelmapperexercise.service.dtos.GamesAllDTO;
import org.modelmapperexercise.util.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ValidatorService validatorService;
    private UserService userService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ValidatorService validatorService, UserService userService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            if (!this.validatorService.isValid(gameAddDTO)) {
                return this.validatorService.validate(gameAddDTO)
                        .stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"));
            }

            Game game = this.modelMapper.map(gameAddDTO, Game.class);
            this.gameRepository.saveAndFlush(game);

            return String.format("Added %s", game.getTitle());
        }
        return "Logged in User is not admin";
    }

    @Override
    public String editGame(long id, Map<String, String> map) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game exists with given id";
        }

        Game game = optionalGame.get();
        String output = String.format("Edited %s", game.getTitle());

        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "title":
                    game.setTitle(entry.getValue());
                    break;
                case "price":
                    game.setPrice(Double.parseDouble(entry.getValue()));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(entry.getValue()));
                    break;
                    //TODO
            }
        }

        this.gameRepository.saveAndFlush(game);

        return output;
    }

    @Override
    public String deleteGame(long id) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return "No such game with given id";
        }
        String output = String.format("Deleted %s", optionalGame.get().getTitle());
        this.gameRepository.delete(optionalGame.get());
        return output;
    }

    @Override
    public Set<GamesAllDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesAllDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String allGamesReadyForPrint() {
        return this.getAllGames().stream().map(GamesAllDTO::toString).collect(Collectors.joining("\n"));
    }
}
