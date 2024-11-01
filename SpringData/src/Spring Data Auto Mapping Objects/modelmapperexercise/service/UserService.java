package org.modelmapperexercise.service;

import org.modelmapperexercise.data.entities.User;
import org.modelmapperexercise.service.dtos.UserLoginDTO;
import org.modelmapperexercise.service.dtos.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logout();

    User getLoggedIn();
}
