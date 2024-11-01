package bg.softuni.modelmapperexer.service;

import bg.softuni.modelmapperexer.data.entities.User;
import bg.softuni.modelmapperexer.service.dtos.UserLoginDTO;
import bg.softuni.modelmapperexer.service.dtos.UserRegisterDTO;

public interface UserService {

    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logout();

    User getLoggedIn();
}
