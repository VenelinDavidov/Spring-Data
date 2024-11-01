package org.modelmapperexercise;

import org.modelmapperexercise.service.GameService;
import org.modelmapperexercise.service.ShoppingCartService;
import org.modelmapperexercise.service.dtos.CartItemDTO;
import org.modelmapperexercise.service.dtos.GameAddDTO;
import org.modelmapperexercise.service.dtos.UserLoginDTO;
import org.modelmapperexercise.service.dtos.UserRegisterDTO;
import org.modelmapperexercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final ShoppingCartService shoppingCartService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.gameService = gameService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();
        while (!input.equals("END")){
            String[] tokens = input.split("\\|");

            String command = "";
            switch (tokens[0]) {
                case "RegisterUser":
                    command = this.userService.registerUser(new UserRegisterDTO(tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
                case "LoginUser":
                    command = this.userService.loginUser(new UserLoginDTO(tokens[1], tokens[2]));
                    break;
                case "Logout":
                    command = this.userService.logout();
                    break;
                case "AddGame":
                    command = this.gameService.addGame(new GameAddDTO(tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]),
                            tokens[4], tokens[5], tokens[6], tokens[7]));
                    break;
                case "EditGame":
                    Map<String, String> map = Arrays.stream(tokens).skip(2).map(p -> p.split("="))
                            .collect(Collectors.toMap(p -> p[0], p -> p[1]));
                    command = this.gameService.editGame(Long.parseLong(tokens[1]), map);
                    break;
                case "DeleteGame":
                    command = this.gameService.deleteGame(Long.parseLong(tokens[1]));
                    break;
                case "AllGames":
                    command = this.gameService.allGamesReadyForPrint();
                    break;
                case "AddItem":
                    command = this.shoppingCartService.addItem(new CartItemDTO(tokens[1]));
                    break;
                case "RemoveItem":
                    command = this.shoppingCartService.deleteItem(new CartItemDTO(tokens[1]));
                    break;
                case "BuyItem":
                    command = this.shoppingCartService.buyItem();
                    break;
            }

            System.out.println(command);
            input = reader.readLine();
        }
    }
}
