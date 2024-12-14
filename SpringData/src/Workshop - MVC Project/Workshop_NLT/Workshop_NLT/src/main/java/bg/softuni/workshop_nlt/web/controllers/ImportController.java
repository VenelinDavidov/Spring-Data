package bg.softuni.workshop_nlt.web.controllers;

import bg.softuni.workshop_nlt.service.CompanyService;
import bg.softuni.workshop_nlt.service.EmployeeService;
import bg.softuni.workshop_nlt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService,
                            ProjectService projectService,
                            EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/xml")
    public ModelAndView xml() {
        boolean[] imported = {
                this.companyService.isImported(),
                this.projectService.isImported(),
                this.employeeService.isImported()
        };

        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        modelAndView.addObject("areImported", imported);
        return modelAndView;
    }



    // company

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");
        modelAndView.addObject("companies",this.companyService.readFile());
        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesPost() throws JAXBException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:xml");
        this.companyService.seedData();
        return modelAndView;

//        return "redirect:xml";
    }




    // project

    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");
        modelAndView.addObject("projects",this.projectService.readFile());
        return modelAndView;
    }

    @PostMapping("/projects")
    public ModelAndView projectsPost() throws JAXBException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:xml");
        this.projectService.seedData();
        return modelAndView;
    }




    // employee

    @GetMapping("/employees")
    public ModelAndView employees() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-employees");
        modelAndView.addObject("employees", this.employeeService.readFile());
        return modelAndView;
    }

    @PostMapping("/employees")
    public ModelAndView seedEmployees() throws JAXBException, IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:xml");
        this.employeeService.seedData();
        return modelAndView;
    }
}