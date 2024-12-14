package bg.softuni.workshop_nlt.web.controllers;

import bg.softuni.workshop_nlt.service.UserService;
import bg.softuni.workshop_nlt.web.model.UserLoginModel;
import bg.softuni.workshop_nlt.web.model.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    // register

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView ("user/register");
        modelAndView.addObject ("time", LocalDate.now ().toString ());
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView registerPost(@ModelAttribute UserRegisterModel userRegisterModel) {
        ModelAndView modelAndView = new ModelAndView ("redirect:/users/login");

        if (!this.userService.validateRegisterModel(userRegisterModel)) {
            modelAndView.setViewName("/user/register");
            modelAndView.addObject("error","Invalid input data / User already exist");
            return modelAndView;
        }
        this.userService.registerUser(userRegisterModel);
        return modelAndView;
    }




    //login

    @GetMapping("/login")
    public ModelAndView login(){
        return  new ModelAndView("/user/login");
    }


    @PostMapping("/login")
    public ModelAndView loginPost(@ModelAttribute UserLoginModel userLoginModel) {
        ModelAndView modelAndView = new ModelAndView("redirect:/home");

        if (!this.userService.loggedIn(userLoginModel)) {
            modelAndView.setViewName("/user/login");
            modelAndView.addObject("error","User not found.");
            return modelAndView;
        }

        return modelAndView;
    }
}
