package bg.softuni.modelmapperexer.service;

import bg.softuni.modelmapperexer.service.dtos.GameAddDTO;
import bg.softuni.modelmapperexer.service.dtos.GamesAllDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


public interface GamesService {

    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);

    Set <GamesAllDTO> getAllGames();

    String allGamesReadyForPrint();
}
