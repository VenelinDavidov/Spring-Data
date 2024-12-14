package bg.softuni.workshop_nlt.service.impl;


import bg.softuni.workshop_nlt.data.entity.Employee;
import bg.softuni.workshop_nlt.data.entity.Project;
import bg.softuni.workshop_nlt.data.repositories.EmployeeRepository;
import bg.softuni.workshop_nlt.data.repositories.ProjectRepository;
import bg.softuni.workshop_nlt.service.EmployeeService;
import bg.softuni.workshop_nlt.service.models.imports.EmployeeImportModel;
import bg.softuni.workshop_nlt.service.models.imports.EmployeeRoodImportModel;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String FILE_PATH = "src/main/resources/files/xmls/employees.xml";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ProjectRepository projectRepository,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean isImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public void seedData() throws JAXBException, IOException {

        XmlMapper xmlMapper = new XmlMapper();

        EmployeeRoodImportModel employeeRoodImportModel =
                xmlMapper.readValue(readFile(), EmployeeRoodImportModel.class);

        employeeRoodImportModel.getEmployees().forEach(this::saveToDb);
    }


    private String saveToDb(EmployeeImportModel employeeImportModel) {

        Employee employee = this.modelMapper.map(employeeImportModel, Employee.class);

        Optional<Project> optionalProject = this.projectRepository.findByName(employeeImportModel.getProject().getName());

        if (optionalProject.isEmpty()) {
            return "Invalid Employee, project not found!";
        }


        employee.setProject(optionalProject.get());
        this.employeeRepository.save(employee);

        return "Employee saved successfully!";
    }


    @Override
    public String getEmployeesAbove25() {

        List<Employee> byAgeGreaterThan = this.employeeRepository.findByAgeGreaterThan(25);

        return byAgeGreaterThan
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

    }
}
