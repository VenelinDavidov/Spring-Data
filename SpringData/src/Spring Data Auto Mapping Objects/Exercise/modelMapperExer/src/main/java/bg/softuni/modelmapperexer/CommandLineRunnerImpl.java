package bg.softuni.modelmapperexer;

import bg.softuni.modelmapperexer.service.GamesService;
import bg.softuni.modelmapperexer.service.ShoppingCartService;
import bg.softuni.modelmapperexer.service.UserService;
import bg.softuni.modelmapperexer.service.dtos.CartItemDTO;
import bg.softuni.modelmapperexer.service.dtos.GameAddDTO;
import bg.softuni.modelmapperexer.service.dtos.UserLoginDTO;
import bg.softuni.modelmapperexer.service.dtos.UserRegisterDTO;
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

    private static final BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));

    private final UserService userService;
    private final GamesService gameService;
    private final ShoppingCartService shoppingCartService;

    public CommandLineRunnerImpl(UserService userService, GamesService gamesService, GamesService gameService, ShoppingCartService cartService) {
        this.userService = userService;
        this.gameService = gameService;
        this.shoppingCartService = cartService;
    }

    @Override
    public void run(String... args) throws Exception {

        String input = reader.readLine ();
        while (!input.equals ("END")) {


            String[] tokens = input.split ("\\|");

            String command = "";
            switch (tokens[0]) {
                case "RegisterUser":
                    //RegisterUser|<email>|<password>|<confirmPassword>|<fullName
                    command = this.userService.registerUser (new UserRegisterDTO (tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
                case "LoginUser":
                    //LoginUser|<email>|<password>
                    command = this.userService.loginUser (new UserLoginDTO (tokens[1], tokens[2]));
                    break;
                case "Logout":
                    command = this.userService.logout ();
                    break;
                //AddGame|<title>|<price>|<size>|<trailer>|<thubnailURL>|<description>|<releaseDate
                case "AddGame":
                    command = this.gameService.addGame (new GameAddDTO (tokens[1], Double.parseDouble (tokens[2]),
                            Double.parseDouble (tokens[3]), tokens[4], tokens[5], tokens[6],
                            LocalDate.parse (tokens[7], DateTimeFormatter.ofPattern ("dd-MM-yyyy"))));
                    break;
                //EditGame|<id>|<values> -> EditGame|1|price=80.00|size=12 -> "price","80.00"
                case "EditGame":
                    Map <String, String> map = Arrays
                            .stream (tokens)
                            .skip (2)
                            .map (p -> p.split ("="))
                            .collect (Collectors.toMap (p -> p[0], p -> p[1]));
                    command = this.gameService.editGame (Long.parseLong (tokens[1]), map);
                    break;
                //DeleteGame|1
                case "DeleteGame":
                    command = this.gameService.deleteGame (Long.parseLong (tokens[1]));
                    break;
                case "AllGames":
                    command = this.gameService.allGamesReadyForPrint ();
                    break;
                //AddItem|Overwatch
                case "AddItem":
                    command = this.shoppingCartService.addItem (new CartItemDTO (tokens[1]));
                    break;
                case "RemoveItem":
                    command = this.shoppingCartService.deleteItem(new CartItemDTO(tokens[1]));
                    break;
                case "BuyItem":
                    command = this.shoppingCartService.buyItem();
                    break;
            }

            System.out.println (command);
            input = reader.readLine ();
        }
    }
}
