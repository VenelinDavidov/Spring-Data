package softuni.exam.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.gson.PersonSeedDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.models.entity.enums.StatusType;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final String FILE_PATH = "src/main/resources/files/json/people.json";

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PersonServiceImpl(PersonRepository personRepository, CompanyRepository companyRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();


        PersonSeedDTO[] personSeedDTOS = this.gson.fromJson(readPeopleFromFile(), PersonSeedDTO[].class);

        for (PersonSeedDTO personSeedDTO : personSeedDTOS) {

            Optional<Person> optionalPerson =
                    this.personRepository.findByFirstNameOrEmailOrPhone(personSeedDTO.getFirstName(),
                    personSeedDTO.getEmail(), personSeedDTO.getPhone());

             if(!this.validationUtil.isValid(personSeedDTO) || optionalPerson.isPresent()) {
               sb.append("Invalid person").append(System.lineSeparator());
               continue;
             }

            Person person = this.modelMapper.map(personSeedDTO, Person.class);
            StatusType statusType = StatusType.valueOf(personSeedDTO.getStatusType());
            person.setStatusType(statusType);
            Country country = this.countryRepository.findById(personSeedDTO.getCountry()).get();
            person.setCountry(country);

            this.personRepository.save(person);

            sb.append(String.format("Successfully imported person %s %s",
                    person.getFirstName(), person.getLastName())).append(System.lineSeparator());
        }


        return sb.toString().trim();
    }
}
