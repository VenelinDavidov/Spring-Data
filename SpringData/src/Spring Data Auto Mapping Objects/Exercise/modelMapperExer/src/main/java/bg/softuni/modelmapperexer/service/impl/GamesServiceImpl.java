package bg.softuni.modelmapperexer.service.impl;

import bg.softuni.modelmapperexer.data.entities.Game;
import bg.softuni.modelmapperexer.data.repositories.GameRepository;
import bg.softuni.modelmapperexer.service.GamesService;
import bg.softuni.modelmapperexer.service.UserService;
import bg.softuni.modelmapperexer.service.dtos.GameAddDTO;
import bg.softuni.modelmapperexer.service.dtos.GamesAllDTO;
import bg.softuni.modelmapperexer.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class GamesServiceImpl implements GamesService {

  private final GameRepository gameRepository;
  private final ModelMapper modelMapper;
  private final ValidatorService validatorService;
  private final UserService userService;

    public GamesServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidatorService validatorService, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validatorService = validatorService;
        this.userService = userService;
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
        return this.getAllGames()
                .stream()
                .map(GamesAllDTO::toString)
                .collect(Collectors.joining("\n"));
    }
}