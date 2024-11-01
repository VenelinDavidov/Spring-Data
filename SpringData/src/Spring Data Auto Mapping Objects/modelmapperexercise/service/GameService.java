package org.modelmapperexercise.service;

import org.modelmapperexercise.service.dtos.GameAddDTO;
import org.modelmapperexercise.service.dtos.GamesAllDTO;

import java.util.Map;
import java.util.Set;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);

    Set<GamesAllDTO> getAllGames();

    String allGamesReadyForPrint();
}
