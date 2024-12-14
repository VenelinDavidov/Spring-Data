package bg.softuni.workshop_nlt.web.controllers;

import bg.softuni.workshop_nlt.service.CompanyService;
import bg.softuni.workshop_nlt.service.EmployeeService;
import bg.softuni.workshop_nlt.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    private final ProjectService projectService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public HomeController(ProjectService projectService, CompanyService companyService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.companyService = companyService;
        this.employeeService = employeeService;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/home")
    public ModelAndView home() {

        boolean isAllDataImported = this.projectService.isImported() &&
                this.companyService.isImported() &&
                this.employeeService.isImported();

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("areImported", isAllDataImported);
        return modelAndView;
    }

}