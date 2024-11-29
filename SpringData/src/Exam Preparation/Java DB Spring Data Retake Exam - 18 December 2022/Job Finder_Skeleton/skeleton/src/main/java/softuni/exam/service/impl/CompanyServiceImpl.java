package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.CompanySeedDTO;
import softuni.exam.models.dto.xml.CompanySeedRoodDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String FILE_PATH = "src/main/resources/files/xml/companies.xml";

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        CompanySeedRoodDTO companySeedRoodDTO = this.xmlParser.fromFile(FILE_PATH, CompanySeedRoodDTO.class);
        for (CompanySeedDTO companySeedDTO : companySeedRoodDTO.getCompanySeedDTOList()) {

            Optional<Company> optionalCompany =
                    this.companyRepository.findByName(companySeedDTO.getCompanyName());

            if(!this.validationUtil.isValid(companySeedDTO) || optionalCompany.isPresent()) {
                sb.append("Invalid company").append(System.lineSeparator());
                continue;
            }

            Company company = this.modelMapper.map(companySeedDTO, Company.class);
            Country country = this.countryRepository.findById(companySeedDTO.getCountryId()).get();
            company.setCountry(country);
            this.companyRepository.saveAndFlush(company);

            sb.append(String.format("Successfully imported company %s - %d",
                    company.getName(),company.getCountry().getId())).append(System.lineSeparator());

        }



        return sb.toString().trim();
    }
}
