package bg.softuni.workshop_nlt.service.impl;

import bg.softuni.workshop_nlt.data.entity.Company;
import bg.softuni.workshop_nlt.data.entity.Project;
import bg.softuni.workshop_nlt.data.repositories.CompanyRepository;
import bg.softuni.workshop_nlt.data.repositories.ProjectRepository;
import bg.softuni.workshop_nlt.service.ProjectService;
import bg.softuni.workshop_nlt.service.models.imports.ProjectImportModel;
import bg.softuni.workshop_nlt.service.models.imports.ProjectRootImportModel;
import bg.softuni.workshop_nlt.util.XmlParser;

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
public class ProjectServiceImpl implements ProjectService {

    private static final String FILE_PATH = "src/main/resources/files/xmls/projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;


    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser, XmlParser xmlParser1) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public boolean isImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public void seedData() throws JAXBException, IOException {

        XmlMapper xmlMapper = new XmlMapper();
        ProjectRootImportModel projectRootImportModel = xmlMapper.readValue(readFile(), ProjectRootImportModel.class);

        projectRootImportModel.getProjects().forEach(project ->
                this.saveToDB(project));


    }


    // Methods import projects
    private String saveToDB(ProjectImportModel project) {
        Project forDbProjects = this.modelMapper.map(project, Project.class);

        Optional<Company> company = this.companyRepository.findByName(forDbProjects.getCompany().getName());

        if (company.isEmpty()) {
            return "Invalid company not found.";
        }

        forDbProjects.setCompany(company.get());

        this.projectRepository.save(forDbProjects);
        return "Successfully imported project.";
    }

    @Override
    public String getFinishedProjects() {

        List<Project> all = this.projectRepository.findByIsFinishedTrue();

      return   all
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
