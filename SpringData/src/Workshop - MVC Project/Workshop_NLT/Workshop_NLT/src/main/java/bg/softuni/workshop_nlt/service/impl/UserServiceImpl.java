package bg.softuni.workshop_nlt.service.impl;

import bg.softuni.workshop_nlt.data.entity.User;
import bg.softuni.workshop_nlt.data.repositories.UserRepository;
import bg.softuni.workshop_nlt.service.UserService;
import bg.softuni.workshop_nlt.web.model.UserLoginModel;
import bg.softuni.workshop_nlt.web.model.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public boolean validateRegisterModel(UserRegisterModel userRegisterModel) {

        return userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword()) &&
                this.userRepository.findByUsername(userRegisterModel.getUsername()).isEmpty() &&
                this.userRepository.findByEmail(userRegisterModel.getEmail()).isEmpty();
    }

    @Override
    public void registerUser(UserRegisterModel userRegisterModel) {
        this.userRepository.saveAndFlush(this.modelMapper.map(userRegisterModel, User.class));
    }


    @Override
    public boolean loggedIn(UserLoginModel userLoginModel) {

        if (this.userRepository.findByUsernameAndPassword
                (userLoginModel.getUsername(),
                 userLoginModel.getPassword()).isEmpty()) {
            return false;
        }

        System.out.printf("Logged in %s%n", userLoginModel.getUsername());
        return true;
    }
}
