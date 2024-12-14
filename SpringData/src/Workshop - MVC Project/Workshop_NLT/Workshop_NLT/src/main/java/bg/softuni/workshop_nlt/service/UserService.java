package bg.softuni.workshop_nlt.service;

import bg.softuni.workshop_nlt.web.model.UserLoginModel;
import bg.softuni.workshop_nlt.web.model.UserRegisterModel;

public interface UserService  {


    boolean validateRegisterModel(UserRegisterModel userRegisterModel);

    void registerUser(UserRegisterModel userRegisterModel);

    boolean loggedIn(UserLoginModel userLoginModel);
}
