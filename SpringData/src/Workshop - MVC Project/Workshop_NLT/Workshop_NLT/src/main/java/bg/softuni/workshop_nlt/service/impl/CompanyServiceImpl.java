package bg.softuni.workshop_nlt.service.impl;

import bg.softuni.workshop_nlt.data.entity.Company;
import bg.softuni.workshop_nlt.data.repositories.CompanyRepository;
import bg.softuni.workshop_nlt.service.CompanyService;
import bg.softuni.workshop_nlt.service.models.imports.CompanyRoodImportModel;
import bg.softuni.workshop_nlt.util.XmlParser;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String FILE_PATH = "src/main/resources/files/xmls/companies.xml";

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean isImported() {
        return this.companyRepository.count() > 0;
    }


    @Override
    public String readFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }


    @Override
    public void seedData() throws JAXBException, IOException {

        XmlMapper xmlMapper = new XmlMapper();
        CompanyRoodImportModel companyRootImportModel = xmlMapper.readValue(readFile(), CompanyRoodImportModel.class);
        //        CompanyRootImportModel companyRootImportModel = this.xmlParser.fromFile(FILE_PATH, CompanyRootImportModel.class);
        companyRootImportModel.getCompanyImportModels().forEach(company -> {
            System.out.println();
            Company mapCompany = this.modelMapper.map(company, Company.class);
            this.companyRepository.saveAndFlush(mapCompany);
        });
    }
}


